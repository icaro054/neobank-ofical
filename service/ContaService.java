package service;

import entity.Conta;
import repository.ContaRepository;
import exception.SaldoInsuficiente; 

public class ContaService {

    private ContaRepository repository = new ContaRepository();

    public void criarConta(Conta novaConta) {
        
        if (novaConta.getNumero() == null || novaConta.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O número da conta é obrigatório.");
        }

        if (novaConta.getSaldo() < 0) {
            throw new IllegalArgumentException("Erro: O saldo inicial da conta não pode ser negativo.");
        }

        if (novaConta.getTipo() == null) {
            throw new IllegalArgumentException("Erro: O tipo da conta (Corrente/Poupança) é obrigatório.");
        }

        if (novaConta.getAgencia() == null) {
            throw new IllegalArgumentException("Erro: A conta precisa estar vinculada a uma Agência válida.");
        }

        if (novaConta.getCliente() == null) {
            throw new IllegalArgumentException("Erro: A conta precisa estar vinculada a um Cliente cadastrado.");
        }

        repository.salvar(novaConta);
    }

    public void depositar(Conta conta, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Erro: O valor do depósito deve ser maior que zero.");
        }
        
        double novoSaldo = conta.getSaldo() + valor;
        conta.setSaldo(novoSaldo);
        
        //O Controller/Main que avisa o sucesso
    }

    public void sacar(Conta conta, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Erro: O valor do saque deve ser maior que zero.");
        }
        
        if (conta.getSaldo() < valor) {
            // 2. Usando a exceção customizada que você criou!
            throw new SaldoInsuficiente("Erro: Saldo insuficiente. Seu saldo atual é R$ " + conta.getSaldo());
        }
        
        double novoSaldo = conta.getSaldo() - valor;
        conta.setSaldo(novoSaldo);
        
        // Sem prints aqui também.
    }

    public Conta buscarContaPorNumero(String numeroConta) {
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da conta é obrigatório!");
        }
        Conta contaEncontrada = repository.buscarPorNumero(numeroConta);
        if (contaEncontrada == null) {
            throw new IllegalArgumentException("Conta não encontrada no sistema!");
        }
        return contaEncontrada;
    }

    public Conta buscarContaPorDocumentoDoCliente(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: Documento é obrigatório para buscar a conta!");
        }
        
        Conta contaEncontrada = repository.buscarPorDocumentoDoCliente(documento);
        
        if (contaEncontrada == null) {
            throw new IllegalArgumentException("Nenhuma conta vinculada a este CPF/CNPJ foi encontrada.");
        }
        
        return contaEncontrada;
    }

    public void transferir(Conta origem, Conta destino, double valor) {
        if (origem.getNumero().equals(destino.getNumero())) {
            throw new IllegalArgumentException("Você não pode transferir para sua própria conta.");
        }
        
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }
        
        this.sacar(origem, valor);
        this.depositar(destino, valor);
    }
}