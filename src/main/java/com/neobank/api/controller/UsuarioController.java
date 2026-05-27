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
            // Limpa a pontuação do CPF (caso o cliente tenha digitado com pontos e traços)
            String loginLimpo = dadosLogin.getLogin() != null ? dadosLogin.getLogin().replaceAll("[^0-9]", "") : "";
            // Tenta logar usando a lógica blindada do Service
            Usuario usuarioLogado = service.fazerLogin(loginLimpo, dadosLogin.getSenha());
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