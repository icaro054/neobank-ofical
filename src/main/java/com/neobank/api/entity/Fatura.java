package com.neobank.api.entity;

public class Fatura extends EntidadeBase {
    private double valor;
    private String descricao;
    private String dataVencimento;
    private boolean paga;
    private Cartao cartao;
    private Cliente cliente;

    public double getValor() {
        return valor;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getDataVencimento() {
        return dataVencimento;
    }
    public boolean isPaga() {
        return paga;
    }
    public Cartao getCartao() {
        return cartao;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public Fatura(double valor, String descricao, String dataVencimento, Cartao cartao, Cliente cliente) {
        this.valor = valor;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.cartao = cartao;
        this.paga = false;
        this.cliente = cliente;
    }
}
