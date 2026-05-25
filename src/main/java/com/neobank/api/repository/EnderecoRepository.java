package com.neobank.api.repository;
import java.util.ArrayList; 
import java.util.List;

import com.neobank.api.entity.Endereco;

public class EnderecoRepository extends GenericRepository<Endereco> {
    // listar todos os endereços de um cliente específico
    public List<Endereco> buscarPorCpfCliente(String documento) {
        List<Endereco> enderecosDoCliente = new ArrayList<>();
        
        for (Endereco e : dados) {
            // Supondo que seu Endereco tenha o Cliente associado
            if (e.getCliente().getDocumento().equals(documento)) {
                enderecosDoCliente.add(e);
            }
        }
        
        return enderecosDoCliente;
    }
    
}
