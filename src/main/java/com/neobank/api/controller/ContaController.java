package com.neobank.api.controller;

import com.neobank.api.entity.Conta;
import com.neobank.api.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.neobank.api.service.TransacaoService;
import java.util.Map;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
@Service
public class ContaController {

    @Autowired
    private ContaService service;

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/criar")
    public Conta criarConta(@RequestBody Conta novaConta) {
        service.criarConta(novaConta);
        return novaConta;
    }

    // Rota que o Dashboard usa pra carregar o saldo assim que o usuário entra
    @GetMapping("/documento/{documento}")
    public ResponseEntity<?> buscarContaPorDocumentoDoCliente(@PathVariable("documento") String documento) {
        try {
            Conta conta = service.buscarContaPorDocumentoDoCliente(documento);
            return ResponseEntity.ok(conta);
        } catch (Throwable e) {
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity.status(500).body(sw.toString());
        }
    }

    @GetMapping("/{numeroConta}")
    public Conta buscarConta(@PathVariable("numeroConta") String numeroConta) {
        return service.buscarContaPorNumero(numeroConta);
    }

    @PostMapping("/depositar")
    public ResponseEntity<?> depositar(@RequestBody Map<String, Object> payload) {
        try {
            String numeroConta = (String) payload.get("numeroConta");
            double valor = Double.parseDouble(payload.get("valor").toString());
            
            Conta conta = service.buscarContaPorNumero(numeroConta);
            service.depositar(conta, valor);
            
            // Logar transacao
            com.neobank.api.entity.Transacao t = new com.neobank.api.entity.Transacao();
            t.setConta(conta);
            t.setValor(valor);
            t.setTipo("DEPOSITO");
            t.setDataHora(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            transacaoService.criarTransacao(t);
            
            return ResponseEntity.ok("Depósito realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sacar")
    public ResponseEntity<?> sacar(@RequestBody Map<String, Object> payload) {
        try {
            String numeroConta = (String) payload.get("numeroConta");
            double valor = Double.parseDouble(payload.get("valor").toString());
            
            Conta conta = service.buscarContaPorNumero(numeroConta);
            service.sacar(conta, valor);
            
            com.neobank.api.entity.Transacao t = new com.neobank.api.entity.Transacao();
            t.setConta(conta);
            t.setValor(valor);
            t.setTipo("SAQUE");
            t.setDataHora(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            transacaoService.criarTransacao(t);

            return ResponseEntity.ok("Saque realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody Map<String, Object> payload) {
        try {
            String numeroOrigem = (String) payload.get("contaOrigem");
            String numeroDestino = (String) payload.get("contaDestino");
            double valor = Double.parseDouble(payload.get("valor").toString());
            
            Conta origem = service.buscarContaPorNumero(numeroOrigem);
            Conta destino = service.buscarContaPorNumero(numeroDestino);
            
            service.transferir(origem, destino, valor);
            
            com.neobank.api.entity.Transacao tSaque = new com.neobank.api.entity.Transacao();
            tSaque.setConta(origem);
            tSaque.setValor(valor);
            tSaque.setTipo("PIX_ENVIADO");
            tSaque.setDataHora(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            transacaoService.criarTransacao(tSaque);

            com.neobank.api.entity.Transacao tDep = new com.neobank.api.entity.Transacao();
            tDep.setConta(destino);
            tDep.setValor(valor);
            tDep.setTipo("PIX_RECEBIDO");
            tDep.setDataHora(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            transacaoService.criarTransacao(tDep);

            return ResponseEntity.ok("Transferência realizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}