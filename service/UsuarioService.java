package service;

import entity.Usuario;
import repository.UsuarioRepository;
import exception.FormatoInvalido;
import exception.LoginEmUso;
import exception.CredenciaisInvalidas;

public class UsuarioService {
    
    private UsuarioRepository repository = new UsuarioRepository();

    public void criarUsuario(Usuario novoUsuario) {
        
        if (novoUsuario.getLogin() == null || novoUsuario.getLogin().trim().isEmpty()) {
            throw new FormatoInvalido("Erro: Login é obrigatório.");
        }
        
        // Regex para garantir que o e-mail tem o formato "nome@provedor.com"
        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (novoUsuario.getEmail() == null || !novoUsuario.getEmail().matches(regexEmail)) {
            throw new FormatoInvalido("Erro: Formato de e-mail inválido. Use um formato como nome@email.com.");
        }
        
        // Senha Forte (Mín. 8 chars, 1 maiúscula, 1 número)
        String regexSenha = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (novoUsuario.getSenha() == null || !novoUsuario.getSenha().matches(regexSenha)) {
            throw new FormatoInvalido("Erro: A senha deve ter no mínimo 8 caracteres, contendo pelo menos 1 número e 1 letra maiúscula.");
        }
        
        // exibe mensagem para duplicação
        Usuario usuarioExistente = repository.buscarPorLogin(novoUsuario.getLogin());
        if (usuarioExistente != null) {
            throw new LoginEmUso("Erro: O login '" + novoUsuario.getLogin() + "' já está em uso. Escolha outro.");
        }

        repository.salvar(novoUsuario);
    }

    public Usuario fazerLogin(String login, String senha) {
        Usuario usuarioEncontrado = repository.buscarPorLogin(login);
        
        // exibe mensagem para falha de login
        if (usuarioEncontrado == null || !usuarioEncontrado.getSenha().equals(senha)) {
            throw new CredenciaisInvalidas("Login ou senha incorretos!");
        }
        
        return usuarioEncontrado;
    }
}