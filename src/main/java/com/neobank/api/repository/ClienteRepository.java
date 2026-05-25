package com.neobank.api.repository;

import com.neobank.api.entity.Cliente;

public class ClienteRepository extends GenericRepository<Cliente> {
    
    public Cliente buscarPorDocumento(String documento) {
        
        for (Cliente c : dados) { 
            if (c.getDocumento().equals(documento)) {
                return c; 
            }
        }
        
        return null; 
    }
}
