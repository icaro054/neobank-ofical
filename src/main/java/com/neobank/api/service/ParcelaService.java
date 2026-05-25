package com.neobank.api.service;

import com.neobank.api.entity.Parcela;
import com.neobank.api.repository.ParcelaRepository;

public class ParcelaService {

    private ParcelaRepository repository = new ParcelaRepository();

    public void criarParcela(Parcela novaParcela) {
        
        if (novaParcela.getNumeroParcelas() <= 0) {
            throw new IllegalArgumentException("Erro: O número da parcela deve ser maior que zero.");
        }

        if (novaParcela.getValorParcela() <= 0) {
            throw new IllegalArgumentException("Erro: O valor da parcela deve ser maior que zero.");
        }

        if (novaParcela.getDataVencimento() == null || novaParcela.getDataVencimento().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A data de vencimento da parcela é obrigatória.");
        }

        if (novaParcela.getFatura() == null) {
            throw new IllegalArgumentException("Erro: Toda parcela precisa estar vinculada a uma Fatura válida.");
        }

        repository.salvar(novaParcela);
    }
}