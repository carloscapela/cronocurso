package com.cronocurso.api.controllers;

import com.cronocurso.api.dtos.DisciplinaRecordDto;
import com.cronocurso.api.models.DisciplinaModel;
import com.cronocurso.api.repositories.DisciplinaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class DisciplinaControllerTest {

    String uri = "disciplinas";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DisciplinaRepository repository;

    @Test
    void storeSuccessAndBadRequest() {
        DisciplinaRecordDto body = new DisciplinaRecordDto("DisciplinaTest");
        webTestClient
            .post()
                .uri(uri)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$").hasJsonPath()
                .jsonPath("$.nome").isEqualTo("DisciplinaTest");

        DisciplinaRecordDto bodyBad = new DisciplinaRecordDto("");
        webTestClient
                .post()
                .uri(uri)
                .bodyValue(bodyBad)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void indexWithAllRegister() {
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").hasJsonPath()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[1].nome").isEqualTo("DisciplinaTest");
    }

    @Test
    void showGetFirstSuccessAndFail() {
        DisciplinaModel model = new DisciplinaModel();
        model.setNome("DisciplinaTest2");

        Optional<DisciplinaModel> entity = Optional.of(repository.save(model));

        assertThat(entity.isPresent()).isEqualTo(true);
        assertThat(entity.isEmpty()).isEqualTo(false);

        DisciplinaModel result = entity.get();

        webTestClient
                .get()
                .uri(uri + "/b2a683f1-4638-40e8-83e2-50b98bd82b51")
                .exchange()
                .expectStatus().isNotFound();

        webTestClient
                .get()
                .uri(uri + "/12345")
                .exchange()
                .expectStatus().isBadRequest();

        webTestClient
                .get()
                .uri(uri + "/" + result.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteWithSuccess() {
        DisciplinaModel model = new DisciplinaModel();
        model.setNome("DisciplinaTest3");

        Optional<DisciplinaModel> entity = Optional.of(repository.save(model));

        assertThat(entity.isPresent()).isEqualTo(true);
        assertThat(entity.isEmpty()).isEqualTo(false);

        DisciplinaModel result = entity.get();

        webTestClient
                .delete()
                .uri(uri + "/" + result.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
}
