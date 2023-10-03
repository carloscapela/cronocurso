package com.cronocurso.api.controllers;

import com.cronocurso.api.dtos.DisciplinaRecordDto;
import com.cronocurso.api.dtos.MateriaRecordDto;
import com.cronocurso.api.models.MateriaModel;
import com.cronocurso.api.repositories.MateriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MateriaController {

    @Autowired
    private MateriaRepository repository;

    @GetMapping(value = "/materias",
            produces = "application/json")
    public ResponseEntity<List<MateriaModel>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @PostMapping(value = "/materias",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<MateriaModel> store(
            @RequestBody @Valid MateriaRecordDto body) {

        var model = new MateriaModel();
        BeanUtils.copyProperties(body, model);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(model));
    }

    @GetMapping(value = "materias/{id}",
            produces = "application/json")
    public ResponseEntity<Object> show(@PathVariable UUID id) {
        Optional<MateriaModel> model = repository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nada encontrado!");
        }

        return ResponseEntity.ok().body(model.get());
    }

    @PutMapping(value = "materias/{id}",
            produces = "application/json")
    public ResponseEntity<Object> update(
            @PathVariable UUID id,
            @RequestBody @Valid DisciplinaRecordDto body) {

        Optional<MateriaModel> model = repository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nada encontrado!");
        }
        var materiaModel = model.get();
        BeanUtils.copyProperties(body, materiaModel);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(materiaModel));
    }

    @DeleteMapping(value = "materias/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<MateriaModel> model = repository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nada encontrado!");
        }
        repository.delete(model.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Registro excluido");
    }
}
