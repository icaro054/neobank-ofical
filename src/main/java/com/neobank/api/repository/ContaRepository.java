package com.neobank.api.repository;

import com.neobank.api.entity.Conta;

public class ContaRepository extends GenericRepository<Conta> {


    public Conta buscarPorNumero(String numero) {
        for (Conta c : dados) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    public Conta buscarPorDocumentoDoCliente(String documento) {
        for (Conta c : dados) {
            if (c.getCliente().getDocumento().equals(documento)) {
                return c; 
            }
        }
        return null; 
    }
}