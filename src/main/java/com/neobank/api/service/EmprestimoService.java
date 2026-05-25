package com.neobank.api.service;

import com.neobank.api.entity.Emprestimo;
import com.neobank.api.repository.EmprestimoRepository;

public class EmprestimoService {

    private EmprestimoRepository repository = new EmprestimoRepository();

    public void criarEmprestimo(Emprestimo novoEmprestimo) {
        
        if (novoEmprestimo.getValorTotal() <= 0) {
            throw new IllegalArgumentException("Erro: O valor total do empréstimo deve ser maior que zero.");
        }

        if (novoEmprestimo.getTaxaJuros() < 0) {
            throw new IllegalArgumentException("Erro: A taxa de juros não pode ser negativa.");
        }

        if (novoEmprestimo.getNumeroParcelas() <= 0) {
            throw new IllegalArgumentException("Erro: O número de parcelas deve ser de pelo menos 1.");
        }

        if (novoEmprestimo.getDataInicio() == null || novoEmprestimo.getDataInicio().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A data de início do empréstimo é obrigatória.");
        }
        if (novoEmprestimo.getDataFim() == null || novoEmprestimo.getDataFim().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A data de fim do empréstimo é obrigatória.");
        }

        if (novoEmprestimo.getCliente() == null) {
            throw new IllegalArgumentException("Erro: O empréstimo precisa estar vinculado a um Cliente cadastrado.");
        }

        if (novoEmprestimo.getConta() == null) {
            throw new IllegalArgumentException("Erro: O empréstimo precisa estar vinculado a uma Conta válida.");
        }

        repository.salvar(novoEmprestimo);
    }
}