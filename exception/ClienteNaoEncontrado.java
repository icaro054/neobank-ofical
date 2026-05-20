package exception;

public class ClienteNaoEncontrado extends RuntimeException {
    public ClienteNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}