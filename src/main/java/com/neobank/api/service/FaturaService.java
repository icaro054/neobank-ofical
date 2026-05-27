package com.neobank.api.service;

import org.springframework.stereotype.Service;

import com.neobank.api.entity.Fatura;
import com.neobank.api.repository.FaturaRepository;

@Service

public class FaturaService {

    private FaturaRepository repository = new FaturaRepository();

    public void criarFatura(Fatura novaFatura) {
        
        if (novaFatura.getValor() <= 0) {
            throw new IllegalArgumentException("Erro: O valor da fatura deve ser maior que zero.");
        }


        if (novaFatura.getDescricao() == null || novaFatura.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A descrição da fatura é obrigatória.");
        }

        if (novaFatura.getDataVencimento() == null || novaFatura.getDataVencimento().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A data de vencimento é obrigatória.");
        }

        if (novaFatura.getCartao() == null) {
            throw new IllegalArgumentException("Erro: A fatura precisa estar obrigatoriamente vinculada a um Cartão.");
        }

        if (novaFatura.getCliente() == null) {
            throw new IllegalArgumentException("Erro: A fatura precisa estar vinculada a um Cliente válido.");
        }

        repository.salvar(novaFatura);
    }
}