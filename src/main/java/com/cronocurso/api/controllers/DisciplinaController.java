package com.cronocurso.api.controllers;

import com.cronocurso.api.dtos.DisciplinaRecordDto;
import com.cronocurso.api.models.DisciplinaModel;
import com.cronocurso.api.repositories.DisciplinaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("/")
    public ResponseEntity<List<DisciplinaModel>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<DisciplinaModel> create(
            @RequestBody @Valid DisciplinaRecordDto bodyDisciplina) {

        var model = new DisciplinaModel();
        BeanUtils.copyProperties(bodyDisciplina, model);

        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaRepository.save(model));
    }
}
