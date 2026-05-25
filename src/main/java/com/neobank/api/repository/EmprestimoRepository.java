package com.neobank.api.repository;

import java.util.ArrayList;
import java.util.List;

import com.neobank.api.entity.Emprestimo;

public class EmprestimoRepository extends GenericRepository<Emprestimo> {
    
    // listar todos os empréstimos de um cliente específico
    public List<Emprestimo> buscarPorTipoCliente(String documento) {
        List<Emprestimo> emprestimosDoCliente = new ArrayList<>();
        
        for (Emprestimo e : dados) {
            // Supondo que seu Emprestimo tenha o Cliente associado
            if (e.getCliente().getTipoCliente().equals(documento)) {
                emprestimosDoCliente.add(e);
            }
        }
        
        return emprestimosDoCliente;
    }
}