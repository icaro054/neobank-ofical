package com.neobank.api.controller;

import com.neobank.api.entity.Cliente;
import com.neobank.api.entity.Usuario;
import com.neobank.api.service.UsuarioService;

public class UsuarioController {
    private UsuarioService service = new UsuarioService();

    public Usuario registrar(String email, String login, String senha, Cliente cliente) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setLogin(login);
        novoUsuario.setSenha(senha);
        novoUsuario.setCliente(cliente);
        novoUsuario.setAtivo(true); // O usuário já nasce ativado
        
        service.criarUsuario(novoUsuario);
        return novoUsuario;
    }

    public Usuario fazerLogin(String login, String senha) {
        return service.fazerLogin(login, senha);
    }
}