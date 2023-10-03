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
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class DisciplinaControllerTest {

    String uri = "disciplinas";
    String uuid = "b2a683f1-4638-40e8-83e2-50b98bd82b51";
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DisciplinaRepository repository;

    @Test
    void storeSuccessAndBadRequest() {
        DisciplinaRecordDto body = new DisciplinaRecordDto("Sku");
        webTestClient
            .post()
                .uri(uri)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$").hasJsonPath()
                .jsonPath("$.nome").isEqualTo("Sku");

        webTestClient
                .post()
                .uri(uri)
                .bodyValue(new DisciplinaRecordDto(""))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void indexWithAllRegister() {
        Long total = repository.count();

        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").hasJsonPath()
                .jsonPath("$.length()").isEqualTo(total)
                .jsonPath("$[1].nome").isEqualTo("Sku");
    }

    @Test
    void showGetFirstSuccessAndFail() {
        DisciplinaModel model = new DisciplinaModel();
        model.setNome("Sku2");

        Optional<DisciplinaModel> entity = Optional.of(repository.save(model));

        assertThat(entity.isPresent()).isEqualTo(true);
        assertThat(entity.isEmpty()).isEqualTo(false);

        DisciplinaModel result = entity.get();

        webTestClient
                .get()
                .uri(uri + "/" + uuid)
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
        model.setNome("Sku3");

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

    @Test
    void updateWithSuccessAndFail() {
        DisciplinaModel model = new DisciplinaModel();
        model.setNome("Sku3");

        Optional<DisciplinaModel> entity = Optional.of(repository.save(model));

        assertThat(entity.isPresent()).isEqualTo(true);
        assertThat(entity.isEmpty()).isEqualTo(false);

        DisciplinaModel result = entity.get();

        DisciplinaRecordDto body = new DisciplinaRecordDto("Sku4");

        webTestClient
                .put()
                .uri(uri + "/" + result.getId())
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").hasJsonPath()
                .jsonPath("$.nome").isEqualTo("Sku4");

        webTestClient
                .put()
                .uri(uri + "/" + uuid)
                .bodyValue(body)
                .exchange()
                .expectStatus().isNotFound();

        webTestClient
                .put()
                .uri(uri + "/123")
                .exchange()
                .expectStatus().isBadRequest();

    }
}
