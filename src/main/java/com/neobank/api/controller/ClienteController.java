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
            clienteService.cadastrarCliente(novoCliente);

            // 2. Monta e salva o Usuário (agora o Login vai funcionar!)
            Usuario novoUsuario = new Usuario();
            novoUsuario.setLogin(dadosDaTela.get("login"));
            novoUsuario.setSenha(dadosDaTela.get("senha"));
            novoUsuario.setEmail(dadosDaTela.get("email"));
            novoUsuario.setCliente(novoCliente); // Amarra o usuário ao cliente
            novoUsuario.setAtivo(true);
            usuarioService.criarUsuario(novoUsuario);

            // 3. Monta e salva uma Conta zerada (agora o Dashboard vai funcionar!)
            Conta novaConta = new Conta();
            // Gera um número de conta aleatório de 6 dígitos
            novaConta.setNumero(String.format("%06d", new Random().nextInt(999999))); 
            novaConta.setCliente(novoCliente);
            novaConta.setSaldo(0.0);
            contaService.criarConta(novaConta);

            return ResponseEntity.ok(novoCliente);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{documento}")
    public Cliente buscarCliente(@PathVariable String documento) {
        return clienteService.buscarClientePorDocumento(documento);
    }
}