package com.neobank.api.service;

import com.neobank.api.entity.Cliente;
import com.neobank.api.exception.ClienteJaCadastrado;
import com.neobank.api.exception.ClienteNaoEncontrado;
import com.neobank.api.exception.DocumentoInvalido;
import com.neobank.api.repository.ClienteRepository;
import com.neobank.api.util.ValidadorDocumento;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    private ClienteRepository repository = new ClienteRepository();

    public void cadastrarCliente(Cliente novoCliente) {
        

        String docLimpo = novoCliente.getDocumento().replaceAll("[^0-9]", "");
        novoCliente.setDocumento(docLimpo); 

        if (novoCliente.getEndereco() == null) {
            throw new IllegalArgumentException("Erro: O endereço completo é obrigatório para o cadastro.");
        }
        if (novoCliente.getEndereco().getCep() == null || novoCliente.getEndereco().getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O CEP do endereço é obrigatório.");
        }
        if (novoCliente.getEndereco().getRua() == null || novoCliente.getEndereco().getRua().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A Rua do endereço é obrigatória.");
        }
        if (novoCliente.getEndereco().getNumero() == null || novoCliente.getEndereco().getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O Número do endereço é obrigatório.");
        }
        if (novoCliente.getEndereco().getCidade() == null || novoCliente.getEndereco().getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A Cidade do endereço é obrigatória.");
        }
        if (novoCliente.getEndereco().getEstado() == null || novoCliente.getEndereco().getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O Estado do endereço é obrigatório.");
        }

        
        if (novoCliente.getTipoCliente().equalsIgnoreCase("PF")) {
            if (!docLimpo.matches("\\d{11}") || !ValidadorDocumento.isCpfValido(docLimpo)) {
                throw new DocumentoInvalido("Erro: CPF inválido. Verifique se os números estão corretos.");
            }
        } else if (novoCliente.getTipoCliente().equalsIgnoreCase("PJ")) {
            if (!docLimpo.matches("\\d{14}") || !ValidadorDocumento.isCnpjValido(docLimpo)) {
                throw new DocumentoInvalido("Erro: CNPJ inválido. Verifique se os números estão corretos.");
            }
        }

        Cliente clienteExistente = repository.buscarPorDocumento(docLimpo);
        if (clienteExistente != null) {
            throw new ClienteJaCadastrado("Erro: Cliente com o documento " + docLimpo + " já possui conta!");
        }

        repository.salvar(novoCliente);
    }

    public void excluirCliente(Cliente cliente) {
        if (cliente != null) {
            repository.deletar(cliente);
        }
    }


    public Cliente buscarClientePorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: CPF ou CNPJ é obrigatório para busca!");
        }
        
        String docLimpoParaBusca = documento.replaceAll("[^0-9]", "");
        Cliente clienteEncontrado = repository.buscarPorDocumento(docLimpoParaBusca);
        
        if (clienteEncontrado == null) {
            // validação de erro específico de busca
            throw new ClienteNaoEncontrado("Nenhum cliente encontrado na base com o documento: " + documento);
        }
        
        return clienteEncontrado;
    }
}