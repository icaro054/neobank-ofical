package com.neobank.api.entity;

public class Emprestimo extends EntidadeBase {
    private String id;
    private double valorTotal;
    private double taxaJuros;
    private int numeroParcelas;
    private String dataInicio;
    private String dataFim;
    private Cliente cliente;
    private Conta conta;

    public String getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public Conta getConta() {
        return conta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}