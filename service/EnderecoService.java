package service;

import entity.Endereco;
import repository.EnderecoRepository;
import exception.FormatoInvalido; 

public class EnderecoService {
    private EnderecoRepository repository = new EnderecoRepository();

    public void criarEndereco(Endereco novoEndereco) {
        
        // Solicita 8 digitos
        if (novoEndereco.getCep() == null || !novoEndereco.getCep().matches("\\d{8}")) {
            throw new FormatoInvalido("Endereço inválido: O CEP deve conter exatamente 8 números (ex: 00000000).");
        }
        
        if (novoEndereco.getRua() == null || novoEndereco.getRua().trim().isEmpty()) {
            throw new FormatoInvalido("Endereço inválido: A rua é obrigatória.");
        }
        
        repository.salvar(novoEndereco);
    }
}