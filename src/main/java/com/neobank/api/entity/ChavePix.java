package com.neobank.api.entity;

public class ChavePix extends EntidadeBase {
    private String valorChave;
    private String tipoChave;
    private Conta conta;
    private Cliente cliente;

    public String getValorChave() {
        return valorChave;
    }

    public void setValorChave (String valorChave) {
        this.valorChave = valorChave;
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
