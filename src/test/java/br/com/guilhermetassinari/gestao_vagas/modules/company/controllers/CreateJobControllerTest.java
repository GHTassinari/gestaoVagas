package br.com.guilhermetassinari.gestao_vagas.modules.company.controllers;

import br.com.guilhermetassinari.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.guilhermetassinari.gestao_vagas.utils.TestUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RequiredArgsConstructor
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateJobControllerTest {
    private MockMvc mvc;

    private final WebApplicationContext webApplicationContext;

    @Value("${security.token.secret.company}")
    private String secretKey;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able to create a new job")
    public void shouldBeAbleToCreateANewJob() throws Exception {

        var createJobDTO = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        var result = mvc.perform(
                MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJSON(createJobDTO))
                        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), secretKey))
                        )
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
