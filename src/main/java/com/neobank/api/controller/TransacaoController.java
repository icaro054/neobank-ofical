package com.neobank.api.controller;

import com.neobank.api.entity.Conta;
import com.neobank.api.entity.Transacao;
import com.neobank.api.service.TransacaoService;

import java.util.List;
// Lembra de importar a data/hora se for usar automaticamente!
import java.time.LocalDateTime; 

public class TransacaoController {
    private TransacaoService service = new TransacaoService();

    public void registrar(Conta conta, double valor, String tipo) {
        Transacao novaTransacao = new Transacao();
        novaTransacao.setConta(conta);
        novaTransacao.setValor(valor);
        novaTransacao.setTipo(tipo);
        // O próprio sistema preenche a hora exata da transação
        novaTransacao.setDataHora(LocalDateTime.now().toString()); 

        service.criarTransacao(novaTransacao);
    }

    
     
    public List<Transacao> exibirExtrato(String numeroConta) {
        return service.buscarPorConta(numeroConta);
    }
}