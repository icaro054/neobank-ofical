package com.neobank.api.controller;

import com.neobank.api.entity.Usuario;
import com.neobank.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@Service
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Rota ativada pelo nosso login.js
    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody Usuario dadosLogin) {
        try {
            // Tenta logar usando a lógica blindada do Service
            Usuario usuarioLogado = service.fazerLogin(dadosLogin.getLogin(), dadosLogin.getSenha());
            return ResponseEntity.ok(usuarioLogado);
        } catch (Exception e) {
            // Errou a senha ou login não existe
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario novoUsuario) {
        service.criarUsuario(novoUsuario);
        return novoUsuario;
    }
}