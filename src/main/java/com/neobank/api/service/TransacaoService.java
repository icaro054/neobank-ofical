package com.neobank.api.service;

import java.util.List;

import com.neobank.api.entity.Transacao;
import com.neobank.api.repository.TransacaoRepository;

public class TransacaoService {

    private TransacaoRepository repository = new TransacaoRepository();

    public void criarTransacao(Transacao novaTransacao) {
        
      
        if (novaTransacao.getValor() <= 0) {
            throw new IllegalArgumentException("Erro: O valor da transação deve ser maior que zero.");
        }

        if (novaTransacao.getDataHora() == null || novaTransacao.getDataHora().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A data e hora da transação são obrigatórias.");
        }

        
        if (novaTransacao.getTipo() == null || novaTransacao.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O tipo da transação é obrigatório.");
        }

       
        if (novaTransacao.getConta() == null) {
            throw new IllegalArgumentException("Erro: A transação precisa estar vinculada a uma Conta.");
        }

        repository.salvar(novaTransacao);
    }


    public List<Transacao> buscarPorConta(String numeroConta) {
        // passa o número da conta para puxar o extrato
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: Número da conta é obrigatório para buscar o extrato.");
        }
        
        // traz todas as transações daquele número
        List<Transacao> extrato = repository.buscarPorConta(numeroConta);
        
        // Se a lista voltar vazia, avisa o usuário
        if (extrato == null || extrato.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma transação encontrada para a conta: " + numeroConta);
        }
        
        return extrato;
    }
}