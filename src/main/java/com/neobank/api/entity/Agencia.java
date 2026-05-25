package com.neobank.api.entity;
public class Agencia extends EntidadeBase {
    private String numero;
    private String nome;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
