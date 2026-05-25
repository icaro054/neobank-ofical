package com.neobank.api.repository;

import java.util.ArrayList;
import java.util.List;

import com.neobank.api.entity.Transacao;

public class TransacaoRepository extends GenericRepository<Transacao> {
    
    
    public List<Transacao> buscarPorConta(String numeroConta) {
        List<Transacao> extrato = new ArrayList<>();
        for (Transacao t : dados) { // Usa a lista "dados" do pai
            if (t.getConta().getNumero().equals(numeroConta)) {
                extrato.add(t);
            }
        }
        return extrato;
    }
}