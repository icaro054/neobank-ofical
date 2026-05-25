package com.neobank.api.service;

import com.neobank.api.entity.ChavePix;
import com.neobank.api.exception.FormatoInvalido;
import com.neobank.api.repository.ChavePixRepository;

public class ChavePixService {

    private ChavePixRepository repository = new ChavePixRepository();

    public void criarChavePix(ChavePix novaChave) {

        String tipo = novaChave.getTipoChave();
        String valor = novaChave.getValorChave();

        if (tipo == null || valor == null) {
            throw new FormatoInvalido("Erro: Tipo e valor da chave PIX são obrigatórios.");
        }

        // Validação dinâmica pelo tipo de chave
        switch (tipo.toUpperCase()) {
            case "CPF":
            case "CNPJ":
                if (!valor.matches("\\d{11}") && !valor.matches("\\d{14}")) {
                    throw new FormatoInvalido("Erro: Chave PIX de documento inválida. Digite apenas números.");
                }
                break;
            case "EMAIL":
                if (!valor.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    throw new FormatoInvalido("Erro: Chave PIX de e-mail com formato inválido.");
                }
                break;
            case "CELULAR":
                if (!valor.matches("\\d{10,11}")) { // ficaria 85999999999
                    throw new FormatoInvalido("Erro: Chave PIX de celular deve conter DDD + Número, apenas dígitos.");
                }
                break;
            default:
                throw new FormatoInvalido("Erro: Tipo de chave PIX não reconhecido (Use CPF, CNPJ, EMAIL ou CELULAR).");
        }

        repository.salvar(novaChave);
    }
}