package com.neobank.api.util;
import java.util.Random;
public class GeradorConta {
    
    // metodo responsavel por gerar numeros de contas
    public static String gerarNumero() {
       return String.format("%05d", new Random().nextInt(100000));
    }
    
}
