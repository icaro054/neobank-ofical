package com.neobank.api.service;

import org.springframework.stereotype.Service;

import com.neobank.api.entity.Agencia;
import com.neobank.api.repository.AgenciaRepository;

@Service

public class AgenciaService {

    private AgenciaRepository repository = new AgenciaRepository();

    public void criarAgencia(Agencia novaAgencia) {
        

        if (novaAgencia.getNumero() == null || novaAgencia.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O número da agência é obrigatório (ex: 0001-9).");
        }

        if (novaAgencia.getNome() == null || novaAgencia.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O nome da agência é obrigatório.");
        }

        repository.salvar(novaAgencia);
    }
}