package com.neobank.api.repository;
import com.neobank.api.entity.Agencia;
public class AgenciaRepository extends GenericRepository<Agencia> {
    
    public Agencia buscarPorNumero(String numeroAgencia) {
        for (Agencia agencia : dados) {
            if (agencia.getNumero().equals(numeroAgencia)) {
                return agencia;
            }
        }
        return null; // Retorna null se a agência não for encontrada
    }
}
