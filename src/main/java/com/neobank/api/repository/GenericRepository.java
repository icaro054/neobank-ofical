package com.neobank.api.repository;

import java.util.ArrayList;
import java.util.List;

import com.neobank.api.entity.EntidadeBase;

import java.util.Collections; 

public abstract class GenericRepository<T extends EntidadeBase> {
    
    protected List<T> dados = new ArrayList<>();

    //  Salvar
    public void salvar(T entidade) {
        if (!dados.contains(entidade)) {
            dados.add(entidade);
        }
    }

    // Buscar Todos
    public List<T> buscarTodos() {
        
        return Collections.unmodifiableList(dados);
    }

    // Excluir
    public void deletar(T entidade) {
        dados.remove(entidade);
    }

}