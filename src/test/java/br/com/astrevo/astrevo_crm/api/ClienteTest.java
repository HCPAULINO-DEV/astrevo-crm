package br.com.astrevo.astrevo_crm.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8086";
    }

    @Test
    public void deveCadastrarCliente() {
        String contato = "user-test";
        String email = "user-test@email.com";
        String telefone = "(11) 91234-5678";
        String documento = "123.456.789-00";
        String tipoPessoa = "FISICA";
        String nomeEmpresa = "Company Test";
        String status = "ATIVO";

        String json = """
                    {
                      "contato": "%s",
                      "email": "%s",
                      "telefone": "%s",
                      "documento": "%s",
                      "tipoPessoa": "%s",
                      "nomeEmpresa": "%s",
                      "status": "%s",
                      "endereco": {
                        "cep": "13309-000",
                        "logradouro": "Rua das Orquídeas",
                        "numero": "123",
                        "complemento": "Bloco B, Apto 204",
                        "bairro": "Jardim Europa",
                        "cidade": "Itu",
                        "estado": "SP",
                        "pais": "Brasil"
                      }
                    }
                """.formatted(contato, email, telefone, documento, tipoPessoa, nomeEmpresa, status);

        given()
                .contentType("application/json")
                .body(json)
                .log().all()
                .when()
                .post("/clientes")
                .then()
                .log().all()
                .statusCode(201)
                .body("contato", equalTo(contato))
                .body("email", equalTo(email))
                .body("telefone", equalTo(telefone))
                .body("documento", equalTo(documento))
                .body("tipoPessoa", equalTo(tipoPessoa))
                .body("nomeEmpresa", equalTo(nomeEmpresa))
                .body("status", equalTo(status))
                .body("endereco", not(empty()));
    }
}
