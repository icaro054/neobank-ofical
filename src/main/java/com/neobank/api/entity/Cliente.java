package com.neobank.api.entity;

public class Cliente extends EntidadeBase {
    private String nome;
    private String documento; // CPF ou o CNPJ
    private String tipoCliente; // "PF" ou "PJ"
    private Endereco endereco;

    // get e set
    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) {
         this.nome = nome; 
        }

    public String getDocumento() {
         return documento; 
        }
    public void setDocumento(String documento) { 
        this.documento = documento; 
    }

    public String getTipoCliente() {
         return tipoCliente; 
        }
    public void setTipoCliente(String tipoCliente) {
         this.tipoCliente = tipoCliente; 
        }

    public Endereco getEndereco() {
         return endereco; 
        }
    public void setEndereco(Endereco endereco) {
         this.endereco = endereco; 
        }
}