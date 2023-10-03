package com.cronocurso.api.controllers;

import com.cronocurso.api.dtos.MateriaRecordDto;
import com.cronocurso.api.models.MateriaModel;
import com.cronocurso.api.repositories.MateriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MateriaControllerTest {

    String uri = "materias";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    MateriaRepository repository;

    @Test
    void storeSuccessAndBadRequest() {
        MateriaRecordDto body = new MateriaRecordDto("SkuTest");
        webTestClient
            .post()
                .uri(uri)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$").hasJsonPath()
                .jsonPath("$.nome").isEqualTo("SkuTest");

        MateriaRecordDto bodyBad = new MateriaRecordDto("");
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
                .jsonPath("$[1].nome").isEqualTo("SkuTest");
    }

    @Test
    void showGetFirstSuccessAndFail() {
        MateriaModel model = new MateriaModel();
        model.setNome("SkuTest2");

        Optional<MateriaModel> entity = Optional.of(repository.save(model));

        assertThat(entity.isPresent()).isEqualTo(true);
        assertThat(entity.isEmpty()).isEqualTo(false);

        MateriaModel result = entity.get();

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
        MateriaModel model = new MateriaModel();
        model.setNome("SkuTest3");

        Optional<MateriaModel> entity = Optional.of(repository.save(model));

        assertThat(entity.isPresent()).isEqualTo(true);
        assertThat(entity.isEmpty()).isEqualTo(false);

        MateriaModel result = entity.get();

        webTestClient
                .delete()
                .uri(uri + "/" + result.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
}
