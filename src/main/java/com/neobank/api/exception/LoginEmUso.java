package com.neobank.api.exception;

public class LoginEmUso extends RuntimeException {
    public LoginEmUso(String mensagem) {
        super(mensagem);
    }
    
}
