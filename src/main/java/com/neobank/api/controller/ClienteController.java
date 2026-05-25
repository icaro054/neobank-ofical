package com.neobank.api.controller;

import com.neobank.api.entity.Cliente;
import com.neobank.api.service.ClienteService;
// import itens web
import org.springframework.web.bind.annotation.*; 

@RestController // faz o java receber requisições web
@RequestMapping("/api/clientes") // O endereço de internet deste controller



public class ClienteController {
    private ClienteService service = new ClienteService();
    @PostMapping
    public Cliente cadastrarClienteWeb(@RequestBody Cliente novoCliente) {
        
        service.cadastrarCliente(novoCliente);
        
     
        return novoCliente; 
    }

    // recebe os dados da tela
    public Cliente registrar(String nome, String documento, String tipoCliente) {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setDocumento(documento);
        novoCliente.setTipoCliente(tipoCliente);
        
        service.cadastrarCliente(novoCliente);
        return novoCliente; // Adicione este retorno!
    }

    
    public Cliente buscarCliente(String documento) {
        return service.buscarClientePorDocumento(documento);
    }

}