package br.com.astrevo.astrevo_crm.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
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

    @Test
    public void deveBuscarClientesCadastrados() {
        //CADASTRAR CLIENTE
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
                .when()
                .post("/clientes");


        //BUSCAR CLIENTES CADASTRADOS
        given()
                .accept("application/json")
                .log().all()
                .when()
                .get("/clientes")
                .then()
                .log().all()
                .statusCode(200)
                .body("content[0].contato", equalTo(contato))
                .body("content[0].email", equalTo(email))
                .body("content[0].telefone", equalTo(telefone))
                .body("content[0].documento", equalTo(documento))
                .body("content[0].tipoPessoa", equalTo(tipoPessoa))
                .body("content[0].nomeEmpresa", equalTo(nomeEmpresa))
                .body("content[0].status", equalTo(status))
                .body("content[0].endereco", notNullValue());
    }

    @Test
    public void deveBuscarClientePeloId() {
        //CADASTRAR CLIENTE
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

        String idCliente =
                given()
                        .contentType("application/json")
                        .body(json)
                        .when()
                        .post("/clientes")
                        .then()
                        .extract()
                        .path("id");

        //BUSCAR CLIENTE CADASTRADO PELO ID
        given()
                .accept("application/json")
                .log().all()
                .when()
                .get("/clientes/" + idCliente)
                .then()
                .log().all()
                .statusCode(200)
                .body("contato", equalTo(contato))
                .body("email", equalTo(email))
                .body("telefone", equalTo(telefone))
                .body("documento", equalTo(documento))
                .body("tipoPessoa", equalTo(tipoPessoa))
                .body("nomeEmpresa", equalTo(nomeEmpresa))
                .body("status", equalTo(status))
                .body("endereco", notNullValue());

    }

}
