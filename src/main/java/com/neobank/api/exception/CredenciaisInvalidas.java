package com.neobank.api.exception;

public class CredenciaisInvalidas extends RuntimeException {
    public CredenciaisInvalidas(String mensagem) {
        super(mensagem);
    }
    
}
