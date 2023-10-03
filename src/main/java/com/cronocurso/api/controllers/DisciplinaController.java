package com.cronocurso.api.controllers;

import com.cronocurso.api.dtos.DisciplinaRecordDto;
import com.cronocurso.api.models.DisciplinaModel;
import com.cronocurso.api.repositories.DisciplinaRepository;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping(value = "/disciplinas",
            produces = "application/json")
    public ResponseEntity<List<DisciplinaModel>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.findAll());
    }

    @PostMapping(value = "/disciplinas",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<DisciplinaModel> store(
            @RequestBody @Valid DisciplinaRecordDto body) {

        var model = new DisciplinaModel();
        BeanUtils.copyProperties(body, model);

        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaRepository.save(model));
    }

    @GetMapping(value = "/disciplinas/{id}",
            produces = "application/json")
    public ResponseEntity<Object> show(@PathVariable UUID id) {
        Optional<DisciplinaModel> model = disciplinaRepository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nada encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(model.get());
    }

    @PutMapping(value = "/disciplinas/{id}",
            produces = "application/json")
    public ResponseEntity<Object> update(
            @PathVariable UUID id,
            @RequestBody @Valid DisciplinaRecordDto body) {

        Optional<DisciplinaModel> model = disciplinaRepository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nada encontrado!");
        }
        var disciplinaModel = model.get();
        BeanUtils.copyProperties(body, disciplinaModel);

        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.save(disciplinaModel));
    }

    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<DisciplinaModel> model = disciplinaRepository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nada encontrado!");
        }
        disciplinaRepository.delete(model.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Registro excluido");
    }
}
