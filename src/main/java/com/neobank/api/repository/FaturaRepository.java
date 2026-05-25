package com.neobank.api.repository;
import java.util.ArrayList;
import java.util.List;

import com.neobank.api.entity.Fatura;

public class FaturaRepository extends GenericRepository<Fatura> {
    // listar todas as faturas de um cliente específico
    public List<Fatura> buscarPorCpfCliente(String documento) {
        List<Fatura> faturasDoCliente = new ArrayList<>();
        
        for (Fatura f : dados) {
            // Supondo que sua Fatura tenha o Cliente associado
            if (f.getCliente().getDocumento().equals(documento)) {
                faturasDoCliente.add(f);
            }
        }
        
        return faturasDoCliente;
    }
    
}
