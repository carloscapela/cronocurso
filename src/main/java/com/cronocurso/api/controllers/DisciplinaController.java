package com.cronocurso.api.controllers;

import com.cronocurso.api.dtos.DisciplinaRecordDto;
import com.cronocurso.api.models.DisciplinaModel;
import com.cronocurso.api.repositories.DisciplinaRepository;
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
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("/disciplinas")
    public ResponseEntity<List<DisciplinaModel>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.findAll());
    }

    @PostMapping("/disciplinas")
    public ResponseEntity<DisciplinaModel> create(
            @RequestBody @Valid DisciplinaRecordDto bodyDisciplina) {

        var model = new DisciplinaModel();
        BeanUtils.copyProperties(bodyDisciplina, model);

        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaRepository.save(model));
    }

    @GetMapping("disciplinas/{id}")
    public ResponseEntity<Object> show(@RequestParam(value = "id") UUID id) {
        Optional<DisciplinaModel> model = disciplinaRepository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina inexistente!");
        }

        return ResponseEntity.ok().body(model.get());
    }

    @PutMapping("disciplinas/{id}")
    public ResponseEntity<Object> update(
            @RequestParam(value = "id") UUID id,
            @RequestBody @Valid DisciplinaRecordDto bodyDisciplina) {

        Optional<DisciplinaModel> model = disciplinaRepository.findById(id);
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina inexistente!");
        }
        var disciplinaModel = model.get();
        BeanUtils.copyProperties(bodyDisciplina, disciplinaModel);

        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.save(disciplinaModel));
    }
}
