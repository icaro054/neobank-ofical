package com.neobank.api.exception;

public class SaldoInsuficiente extends RuntimeException {
    public SaldoInsuficiente(String mensagem) {
        super(mensagem);
    }
}