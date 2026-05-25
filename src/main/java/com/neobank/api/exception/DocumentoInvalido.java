package com.neobank.api.exception;

public class DocumentoInvalido extends RuntimeException {
    public DocumentoInvalido(String mensagem) {
        super(mensagem);
    }
}