package com.neobank.api.controller;

import com.neobank.api.entity.Cliente;
import com.neobank.api.entity.Usuario;
import com.neobank.api.entity.Conta;
import com.neobank.api.service.ClienteService;
import com.neobank.api.service.UsuarioService;
import com.neobank.api.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

import com.neobank.api.entity.Agencia;
import com.neobank.api.entity.TipoConta;
import com.neobank.api.util.GeradorConta;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContaService contaService;

    @PostMapping("/registrar")
    public ResponseEntity<?> cadastrarClienteWeb(@RequestBody Map<String, String> dadosDaTela) {
        try {
            // 1. Monta e salva o Cliente
            Cliente novoCliente = new Cliente();
            novoCliente.setNome(dadosDaTela.get("nome"));
            novoCliente.setDocumento(dadosDaTela.get("documento"));
            novoCliente.setTipoCliente(dadosDaTela.get("tipoCliente"));

            // Mapeia o endereço
            com.neobank.api.entity.Endereco novoEndereco = new com.neobank.api.entity.Endereco();
            novoEndereco.setCep(dadosDaTela.get("cep"));
            novoEndereco.setRua(dadosDaTela.get("rua"));
            novoEndereco.setNumero(dadosDaTela.get("numero"));
            novoEndereco.setBairro(dadosDaTela.get("bairro"));
            novoEndereco.setCidade(dadosDaTela.get("cidade"));
            novoEndereco.setEstado(dadosDaTela.get("estado"));
            
            novoCliente.setEndereco(novoEndereco);

            clienteService.cadastrarCliente(novoCliente);

            // 2. Monta e salva o Usuário
            Usuario novoUsuario = new Usuario();
            String docLimpo = novoCliente.getDocumento().replaceAll("[^0-9]", "");
            novoUsuario.setLogin(docLimpo); // Usa o documento como login
            novoUsuario.setSenha(dadosDaTela.get("senha"));
            novoUsuario.setEmail(dadosDaTela.get("email"));
            novoUsuario.setCliente(novoCliente); // Amarra o usuário ao cliente
            novoUsuario.setAtivo(true);
            
            try {
                usuarioService.criarUsuario(novoUsuario);
            } catch (Exception e) {
                // Se o usuário quebrar na senha/email, deletamos o Cliente que já havia sido salvo na linha de cima pra não "sujar" o CPF na base
                clienteService.excluirCliente(novoCliente);
                throw e; // repassa o erro pra cair no catch principal
            }

            // 3. Monta e salva uma Conta
            Conta novaConta = new Conta();
            novaConta.setNumero(GeradorConta.gerarNumero()); 
            novaConta.setCliente(novoCliente);
            novaConta.setSaldo(0.0);
            
            Agencia agencia = new Agencia();
            agencia.setNome("Agência Central");
            agencia.setNumero("001");
            novaConta.setAgencia(agencia);
            
            String tipo = dadosDaTela.get("tipoConta");
            if(tipo != null && tipo.equalsIgnoreCase("POUPANCA")) {
                novaConta.setTipo(TipoConta.POUPANCA);
            } else {
                novaConta.setTipo(TipoConta.CORRENTE);
            }
            
            try {
                contaService.criarConta(novaConta);
            } catch (Exception e) {
                usuarioService.excluirUsuarioPorLogin(docLimpo);
                clienteService.excluirCliente(novoCliente);
                throw e;
            }

            // Responde com a conta para o front poder exibir o número
            return ResponseEntity.ok(novaConta);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{documento}")
    public Cliente buscarCliente(@PathVariable("documento") String documento) {
        return clienteService.buscarClientePorDocumento(documento);
    }

    @DeleteMapping("/excluir/{documento}")
    public ResponseEntity<?> excluirConta(@PathVariable("documento") String documento) {
        try {
            String docLimpo = documento.replaceAll("[^0-9]", "");
            Cliente cliente = clienteService.buscarClientePorDocumento(docLimpo);
            
            // Exclui a conta bancária
            try {
                Conta conta = contaService.buscarContaPorDocumentoDoCliente(docLimpo);
                contaService.excluirConta(conta);
            } catch(Exception ignored) { }
            
            // Exclui o acesso
            try {
                com.neobank.api.entity.Usuario usuario = usuarioService.fazerLogin(docLimpo, cliente.getNome()); // workaround if we don't have buscarPorLogin public, but we can call excluir
                // we will create a dedicated method in UsuarioService to delete by login
            } catch(Exception e){}
            
            usuarioService.excluirUsuarioPorLogin(docLimpo);
            clienteService.excluirCliente(cliente);
            
            return ResponseEntity.ok("Conta excluída com sucesso.");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}