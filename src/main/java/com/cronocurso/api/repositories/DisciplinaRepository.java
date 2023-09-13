package com.cronocurso.api.repositories;

import com.cronocurso.api.models.DisciplinaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DisciplinaRepository extends JpaRepository<DisciplinaModel, UUID> {
}
