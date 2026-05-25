package com.neobank.api.entity;
import java.util.UUID;
public abstract class EntidadeBase {
    // geração de id automático 
    private String id;
    
    public EntidadeBase() {
    this.id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

}
