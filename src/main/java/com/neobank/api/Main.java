package com.neobank.api;

import com.neobank.api.controller.*;
import com.neobank.api.entity.*;
import com.neobank.api.exception.*;
import com.neobank.api.util.*;
import java.util.*; 

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ClienteController clienteController = new ClienteController();
        ContaController contaController = new ContaController();
        TransacaoController transacaoController = new TransacaoController();
        UsuarioController usuarioController = new UsuarioController();

        // variavéis responsáveis por login
        Conta contaLogada = null;
        int opcao = -1;

        ChavePix pixAtual = null;
        Cartao cartaoAtual = null;
        Fatura faturaAtual = null;
        Emprestimo emprestimoAtual = null;

       
        System.out.println( "\n Bem-vindo(a) ao NeoBank! Boa noite :D");
        

        do {
            try {
                // menu 1 - login ou criar conta
                if (contaLogada == null) {
                    System.out.println("\n--- TELA INICIAL ---");
                    System.out.println("1 - Abrir Nova Conta");
                    System.out.println("2 - Acessar Conta (Login)");
                    System.out.println("0 - Desligar Sistema");
                    System.out.print("Escolha: ");

                    opcao = sc.nextInt();
                    sc.nextLine(); 

                    switch (opcao) {
                        case 1:
                            System.out.println("\n--- ABERTURA DE CONTA ---");
                            System.out.print("Digite seu nome: ");
                            String nome = sc.nextLine();

                            System.out.print("Você é Pessoa Física (PF) ou Jurídica (PJ)? ");
                            String tipoCliente = sc.nextLine();

                            System.out.print("Digite seu Documento (CPF ou CNPJ): ");
                            String documento = sc.nextLine();

                            Cliente cliente = clienteController.registrar(nome, documento, tipoCliente);

                            Endereco endereco = new Endereco();
                            System.out.print("Rua: ");
                            endereco.setRua(sc.nextLine());
                            System.out.print("Número: ");
                            endereco.setNumero(sc.nextLine());
                            System.out.print("Bairro: ");
                            endereco.setBairro(sc.nextLine());
                            System.out.print("Cidade: ");
                            endereco.setCidade(sc.nextLine());
                            System.out.print("Estado: ");
                            endereco.setEstado(sc.nextLine());
                            System.out.print("CEP (Somente números): ");
                            endereco.setCep(sc.nextLine());
                            
                            // salva o endereço do cliente
                            cliente.setEndereco(endereco);

                            Agencia agencia = new Agencia();
                            agencia.setNome("Agência Central");
                            agencia.setNumero("001");

                            // uso do gerador de numero de conta
                            String numConta = GeradorConta.gerarNumero();
                            System.out.println("\n Número da sua nova conta gerado: " + numConta);

                            Conta novaConta = contaController.criarConta(numConta, cliente, agencia, TipoConta.CORRENTE);

                            System.out.print("Cadastre uma chave PIX para finalizar a conta: ");
                            String chavePix = sc.nextLine();

                            pixAtual = new ChavePix();
                            pixAtual.setValorChave(chavePix);
                            pixAtual.setTipoChave("EMAIL");
                            pixAtual.setConta(novaConta);

                            cartaoAtual = new Cartao();
                            cartaoAtual.setNumero("1234 5678 0000 9999");
                            cartaoAtual.setNomeTitular(cliente.getNome());
                            cartaoAtual.setValidade("12/30");
                            cartaoAtual.setCvv("123");
                            cartaoAtual.setLimite(3000);
                            cartaoAtual.setTipo("CREDITO");
                            cartaoAtual.setConta(novaConta);

                            faturaAtual = new Fatura(350.0, "Compra Mercado", "10/05/2026", cartaoAtual, cliente);

                            emprestimoAtual = new Emprestimo();
                            emprestimoAtual.setValorTotal(5000);
                            emprestimoAtual.setTaxaJuros(0.02);
                            emprestimoAtual.setNumeroParcelas(12);
                            emprestimoAtual.setDataInicio("01/04/2026");
                            emprestimoAtual.setDataFim("01/04/2027");
                            emprestimoAtual.setConta(novaConta);

                            // cria os dados de acesso do user
                            System.out.println("\nCRIAR DADOS DE ACESSO ");
                            System.out.print("Digite seu Email: ");
                            String email = sc.nextLine();
                            System.out.print("Crie um Login: ");
                            String login = sc.nextLine();
                            
                            // MENSAGEM ATUALIZADA PARA A REGRA DE SENHA FORTE
                            System.out.print("Crie uma Senha (mínimo 8 caracteres, 1 maiúscula e 1 número): ");
                            String senha = sc.nextLine();

                            usuarioController.registrar(email, login, senha, cliente);

                            // aqui envia os dados de conta criada
                            contaLogada = novaConta;
                            System.out.println("\n Conta e Usuário criados com sucesso! Você já está logado.");
                            break;

                        case 2:
                            System.out.println("\n--- LOGIN NEOBANK ---");
                            System.out.print("Login: ");
                            String loginDigitado = sc.nextLine();
                            
                            System.out.print("Senha: ");
                            String senhaDigitada = sc.nextLine();

                            try {
                                
                                Usuario usuarioLogado = usuarioController.fazerLogin(loginDigitado, senhaDigitada);
                                
                                String docDoCliente = usuarioLogado.getCliente().getDocumento();
                                
                                contaLogada = contaController.buscarContaPorDocumentoDoCliente(docDoCliente);
                                
                                System.out.println("\n Login aprovado! Bem-vindo(a) de volta, " + usuarioLogado.getCliente().getNome() + "!");
                                
                            } catch (IllegalArgumentException e) {
                                System.out.println("\n ACESSO NEGADO: " + e.getMessage());
                            } catch (CredenciaisInvalidas e) {
                                System.out.println("\n 🚫 AVISO NEOBANK: " + e.getMessage());
                            }
                            break;

                        case 0:
                            System.out.println("\nEncerrando o sistema...");
                            break;

                        default:
                            System.out.println("\nOpção inválida!");
                            break;
                    }
                } 
                // menu 2 - opções para usuário logado
                else {
                    System.out.println("\n--- CAIXA ELETRÔNICO | CONTA: " + contaLogada.getNumero() + " ---");
                    System.out.println("1 - Depositar");
                    System.out.println("2 - Sacar");
                    System.out.println("3 - Ver Extrato de Transações");
                    System.out.println("4 - Ver Resumo Completo da Conta (Produtos)");
                    System.out.println("5 - Transferência (PIX)");
                    System.out.println("8 - Sair da Conta (Logout)");
                    System.out.println("0 - Desligar Sistema");
                    System.out.print("Escolha: ");

                    opcao = sc.nextInt();
                    sc.nextLine(); 

                    switch (opcao) {
                        case 1:
                            System.out.print("Valor do depósito: R$ ");
                            double dep = sc.nextDouble();
                            sc.nextLine();
                            contaController.depositar(contaLogada, dep);
                            transacaoController.registrar(contaLogada, dep, "DEPOSITO");
                            System.out.println("\nDepósito de R$ " + dep + " realizado com sucesso!");
                            break;

                        case 2:
                            System.out.print("Valor do saque: R$ ");
                            double saque = sc.nextDouble();
                            sc.nextLine();
                            // Try/Catch removido daqui para deixar o catch global cuidar disso
                            contaController.sacar(contaLogada, saque);
                            transacaoController.registrar(contaLogada, saque, "SAQUE");
                            System.out.println("\nSaque de R$ " + saque + " autorizado com sucesso!");
                            break;

                        case 3:
                            List<Transacao> extrato = transacaoController.exibirExtrato(contaLogada.getNumero());
                            System.out.println("\n EXTRATO BANCÁRIO ");
                            for (Transacao t : extrato) {
                                System.out.println("[ " + t.getTipo() + " ] - R$ " + t.getValor());
                            }
                            break;

                        case 4:
                            System.out.println("\n RESUMO DE PRODUTOS NEOBANK ");
                            System.out.println("Cliente: " + contaLogada.getCliente().getNome() + " (Doc: " + contaLogada.getCliente().getDocumento() + " - " + contaLogada.getCliente().getTipoCliente() + ")");
                            System.out.println("Conta: " + contaLogada.getNumero() + " | Agência: " + contaLogada.getAgencia().getNumero());
                            System.out.println("Saldo Atual: R$ " + contaLogada.getSaldo());
                            if (pixAtual != null) System.out.println("Chave PIX Ativa: " + pixAtual.getValorChave());
                            if (cartaoAtual != null) System.out.println("Cartão de Crédito final " + cartaoAtual.getNumero().substring(15) + " | Limite: R$ " + cartaoAtual.getLimite());
                            if (faturaAtual != null) System.out.println("Fatura Atual: R$ " + faturaAtual.getValor() + " (Venc: " + faturaAtual.getDataVencimento() + ")");
                            if (emprestimoAtual != null) System.out.println("Empréstimo Ativo: R$ " + emprestimoAtual.getValorTotal() + " (12 parcelas)");
                            break;
                            
                        case 5:
                            System.out.println("\n--- ÁREA PIX / TRANSFERÊNCIA ---");
                            System.out.print("Digite o número da conta de destino: ");
                            String numeroDestino = sc.nextLine();
                            
                            System.out.print("Valor da transferência: R$ ");
                            double valorTransferencia = sc.nextDouble();
                            sc.nextLine(); 
                            
                            // Try/Catch removido daqui para deixar o catch global cuidar disso
                            Conta contaDestino = contaController.buscarConta(numeroDestino);
                            contaController.transferir(contaLogada, contaDestino, valorTransferencia);
                            
                            transacaoController.registrar(contaLogada, valorTransferencia, "PIX ENVIADO");
                            transacaoController.registrar(contaDestino, valorTransferencia, "PIX RECEBIDO");
                            
                            System.out.println("\nTransferência de R$ " + valorTransferencia + " enviada com sucesso para " + contaDestino.getCliente().getNome() + "!");
                            break;

                        case 8:
                            System.out.println("\nSaindo da conta " + contaLogada.getNumero() + "...");
                            contaLogada = null; 
                            break;

                        case 0:
                            System.out.println("\nEncerrando o sistema... Obrigado por usar o NeoBank!");
                            break;

                        default:
                            System.out.println("\nOpção inválida!");
                            break;
                    }
                }
            // super mensagens
            } catch (InputMismatchException e) {
                System.out.println("\n Erro: Por favor, digite um número válido nas opções de menu.");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("\n ERRO: " + e.getMessage());
            } catch (DocumentoInvalido | ClienteJaCadastrado | ClienteNaoEncontrado | SaldoInsuficiente | FormatoInvalido | LoginEmUso | CredenciaisInvalidas e) {
                System.out.println("\n AVISO NEOBANK: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n Erro inesperado do Sistema: " + e.getMessage());
            }


        } while (opcao != 0);

        sc.close();
    }
}