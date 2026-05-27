package com.neobank.api.controller;

import com.neobank.api.entity.Conta;
import com.neobank.api.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
@Service
public class ContaController {

    @Autowired
    private ContaService service;

    @PostMapping("/criar")
    public Conta criarConta(@RequestBody Conta novaConta) {
        service.criarConta(novaConta);
        return novaConta;
    }

    // Rota que o Dashboard usa pra carregar o saldo assim que o usuário entra
    @GetMapping("/documento/{documento}")
    public ResponseEntity<?> buscarContaPorDocumentoDoCliente(@PathVariable String documento) {
        try {
            Conta conta = service.buscarContaPorDocumentoDoCliente(documento);
            return ResponseEntity.ok(conta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{numeroConta}")
    public Conta buscarConta(@PathVariable String numeroConta) {
        return service.buscarContaPorNumero(numeroConta);
    }

    // Mantive os métodos base que vamos plugar nos botões depois
    public void depositar(Conta conta, double valor) {
        service.depositar(conta, valor);
    }

    public void sacar(Conta conta, double valor) {
        service.sacar(conta, valor);
    }

    public void transferir(Conta origem, Conta destino, double valor) {
        service.transferir(origem, destino, valor);
    }
}