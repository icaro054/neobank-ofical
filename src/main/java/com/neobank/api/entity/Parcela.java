package com.neobank.api.entity;

public class Parcela extends EntidadeBase {
    private int numeroParcelas;
    private double valorParcela;
    private String dataVencimento;
    private boolean paga;
    private Fatura fatura;

    public int getNumeroParcelas() {
        return numeroParcelas;
    }
    public double getValorParcela() {
        return valorParcela;
    }
    public String getDataVencimento() {
        return dataVencimento;
    }
    public boolean isPaga() {
        return paga;
    }
    public Fatura getFatura() {
        return fatura;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }
    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    public void setPaga(boolean paga) {
        this.paga = paga;
    }
    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }
}