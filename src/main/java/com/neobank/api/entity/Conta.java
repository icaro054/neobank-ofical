package com.neobank.api.entity;

public class Conta extends EntidadeBase {
    private String numero;
    private double saldo;
    private Cliente cliente;
    private TipoConta tipo;
    private Agencia agencia;

    public String getNumero() {
         return numero; 
        }

    public void setNumero(String numero) {
         this.numero = numero; 
        }

    public double getSaldo() {
         return saldo; 
        }
        
    public void setSaldo(double saldo) {
         this.saldo = saldo; 
        }

    public Cliente getCliente() {
         return cliente; 
        }
    public void setCliente(Cliente cliente) {
         this.cliente = cliente; 
        }

    public TipoConta getTipo() {
         return tipo; 
        }
    public void setTipo(TipoConta tipo) {
         this.tipo = tipo; 
        }

    
    public Agencia getAgencia() {
         return agencia; 
        }
    public void setAgencia(Agencia agencia) {
         this.agencia = agencia; 
        }
}