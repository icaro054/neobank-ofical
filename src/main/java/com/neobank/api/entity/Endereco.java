package com.neobank.api.entity;

public class Endereco extends EntidadeBase {
    private String cep;
    private String rua;
    private String numero;
    private String cidade;
    private String bairro;
    private String estado;
    private Cliente cliente;
    private Conta conta;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public Conta getConta() {
        return conta;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public void setConta(Conta conta) {
        this.conta = conta;
    }


}
