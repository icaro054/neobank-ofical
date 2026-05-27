package com.neobank.api.service;

import org.springframework.stereotype.Service;

import com.neobank.api.entity.Cartao;
import com.neobank.api.exception.FormatoInvalido;
import com.neobank.api.repository.CartaoRepository;

@Service

public class CartaoService {

    private CartaoRepository repository = new CartaoRepository();

    public void criarCartao(Cartao novoCartao) {
        
        // Exige 16 digitos 
        if (novoCartao.getNumero() == null || !novoCartao.getNumero().matches("\\d{16}")) {
            throw new FormatoInvalido("Cartão inválido: O número deve conter exatamente 16 números.");
        }

        if (novoCartao.getNomeTitular() == null || novoCartao.getNomeTitular().trim().isEmpty()) {
            throw new FormatoInvalido("Cartão inválido: O nome do titular é obrigatório.");
        }

        // Exige 3 digitos 
        if (novoCartao.getCvv() == null || !novoCartao.getCvv().matches("\\d{3}")) {
            throw new FormatoInvalido("Cartão inválido: O CVV deve conter exatamente 3 números.");
        }

        // Exige o formato MM/YY 
        if (novoCartao.getValidade() == null || !novoCartao.getValidade().matches("(0[1-9]|1[0-2])/\\d{2}")) {
            throw new FormatoInvalido("Cartão inválido: A validade deve estar no formato MM/YY (ex: 12/30).");
        }

        if (novoCartao.getLimite() < 0) {
            throw new FormatoInvalido("Cartão inválido: O limite do cartão não pode ser negativo.");
        }
        
        repository.salvar(novoCartao);
    }
}