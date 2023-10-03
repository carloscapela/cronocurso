package com.cronocurso.api.repositories;

import com.cronocurso.api.models.MateriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MateriaRepository  extends JpaRepository<MateriaModel, UUID> {
}
