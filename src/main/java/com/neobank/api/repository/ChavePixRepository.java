package com.neobank.api.repository;
import java.util.ArrayList;
import java.util.List;

import com.neobank.api.entity.ChavePix;
public class ChavePixRepository extends GenericRepository<ChavePix> {
    // listar todas as chaves Pix de um cliente específico
    public List<ChavePix> buscarPorTipoCliente(String documento) {
        List<ChavePix> chavesDoCliente = new ArrayList<>();
        
        for (ChavePix c : dados) {
            // Supondo que sua ChavePix tenha o Cliente associado
            if (c.getCliente().getTipoCliente().equals(documento)) {
                chavesDoCliente.add(c);
            }
        }
        
        return chavesDoCliente;
    }
    
}
