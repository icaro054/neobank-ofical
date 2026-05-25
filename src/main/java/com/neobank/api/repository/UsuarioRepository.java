package com.neobank.api.repository;

import com.neobank.api.entity.Usuario;

// segue o repositorio generico
public class UsuarioRepository extends GenericRepository<Usuario> {
    
    public Usuario buscarPorLogin(String login) {
        for (Usuario u : dados) { 
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null; 
    }
}