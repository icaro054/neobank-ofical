package com.neobank.api.controller;

import com.neobank.api.entity.Conta;
import com.neobank.api.entity.Transacao;
import com.neobank.api.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
@Service

public class TransacaoController {

    @Autowired
    private TransacaoService service;

    // Rota que devolve a lista de transações pro HTML montar o extrato
    @GetMapping("/extrato/{numeroConta}")
    public List<Transacao> exibirExtrato(@PathVariable String numeroConta) {
        return service.buscarPorConta(numeroConta);
    }
    
    public void registrar(Conta conta, double valor, String tipo) {
        Transacao novaTransacao = new Transacao();
        novaTransacao.setConta(conta);
        novaTransacao.setValor(valor);
        novaTransacao.setTipo(tipo);
        
        // Gerando a data da transação no padrão brasileiro 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        novaTransacao.setDataHora(LocalDateTime.now().format(formatter)); 

        service.criarTransacao(novaTransacao);
    }
}