package com.neobank.api.controller;

import com.neobank.api.entity.Agencia;
import com.neobank.api.entity.Cliente;
import com.neobank.api.entity.Conta;
import com.neobank.api.entity.TipoConta;
import com.neobank.api.service.ContaService;

public class ContaController {
    private ContaService service = new ContaService();

    // A tela passa os dados, o Controller monta a Conta
    public Conta criarConta(String numeroConta, Cliente cliente, Agencia agencia, TipoConta tipo) {
        Conta novaConta = new Conta();
        novaConta.setNumero(numeroConta);
        novaConta.setCliente(cliente);
        novaConta.setAgencia(agencia);
        novaConta.setTipo(tipo);
        novaConta.setSaldo(0.0); // Toda conta nasce zerada

        service.criarConta(novaConta);
        return novaConta;
    }

     
    public void depositar(Conta conta, double valor) {
        service.depositar(conta, valor);
    }

    public void sacar(Conta conta, double valor) {
        service.sacar(conta, valor);
    }

    public Conta buscarConta(String numeroConta) {
        return service.buscarContaPorNumero(numeroConta);
    }
    
    public Conta buscarContaPorDocumentoDoCliente(String documento) {
        return service.buscarContaPorDocumentoDoCliente(documento);
    }

    public void transferir(Conta origem, Conta destino, double valor) {
        service.transferir(origem, destino, valor);
    }
}