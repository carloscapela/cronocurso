package com.cronocurso.api.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "materias")
public class MateriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
