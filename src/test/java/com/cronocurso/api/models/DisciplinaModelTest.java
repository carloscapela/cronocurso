package com.cronocurso.api.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DisciplinaModelTest {
    @Test
    void hasValuesInModel() {
        DisciplinaModel disciplinaModel = new DisciplinaModel();
        disciplinaModel.setNome("disciplinaTest");

        assertThat(disciplinaModel.getNome()).isEqualTo("disciplinaTest");
    }
}
