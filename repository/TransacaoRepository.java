package repository;

import entity.Transacao;
import java.util.ArrayList;
import java.util.List;

public class TransacaoRepository extends GenericRepository<Transacao> {
    
    
    public List<Transacao> buscarPorConta(String numeroConta) {
        List<Transacao> extrato = new ArrayList<>();
        for (Transacao t : dados) { // Usa a lista "dados" do pai
            if (t.getConta().getNumero().equals(numeroConta)) {
                extrato.add(t);
            }
        }
        return extrato;
    }
}