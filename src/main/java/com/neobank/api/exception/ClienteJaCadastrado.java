package com.neobank.api.exception;

public class ClienteJaCadastrado extends RuntimeException {
    public ClienteJaCadastrado(String mensagem) {
        super(mensagem);
    }
}