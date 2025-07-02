package br.com.astrevo.astrevo_crm.api;

import br.com.astrevo.astrevo_crm.repository.ClienteRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        clienteRepository.deleteAll();
    }

    private String clienteJson() {
        return """
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
                """;
    }

    private CadastroClienteDto cadastrarCliente() {
        String contato = "user-test";
        String email = "user-test@email.com";
        String telefone = "(11) 91234-5678";
        String documento = "123.456.789-00";
        String tipoPessoa = "FISICA";
        String nomeEmpresa = "Company Test";
        String status = "ATIVO";

        String json = clienteJson().formatted(contato, email, telefone, documento, tipoPessoa, nomeEmpresa, status);

        String idCliente =
                given()
                        .contentType("application/json")
                        .body(json)
                        .when()
                        .post("/clientes")
                        .then()
                        .extract()
                        .path("id");

        return new CadastroClienteDto(idCliente, clienteJson(), contato, email, telefone, documento, tipoPessoa, nomeEmpresa, status);
    }

    private record CadastroClienteDto(String idCliente, String json, String contato, String email, String telefone,
                                      String documento, String tipoPessoa, String nomeEmpresa, String status) {
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

        String json = clienteJson().formatted(contato, email, telefone, documento, tipoPessoa, nomeEmpresa, status);

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
        CadastroClienteDto cliente = cadastrarCliente();


        //BUSCAR CLIENTES CADASTRADOS
        given()
                .accept("application/json")
                .log().all()
                .when()
                .get("/clientes")
                .then()
                .log().all()
                .statusCode(200)
                .body("content[0].contato", equalTo(cliente.contato))
                .body("content[0].email", equalTo(cliente.email))
                .body("content[0].telefone", equalTo(cliente.telefone))
                .body("content[0].documento", equalTo(cliente.documento))
                .body("content[0].tipoPessoa", equalTo(cliente.tipoPessoa))
                .body("content[0].nomeEmpresa", equalTo(cliente.nomeEmpresa))
                .body("content[0].status", equalTo(cliente.status))
                .body("content[0].endereco", notNullValue());
    }

    @Test
    public void deveBuscarClientePeloId() {
        //CADASTRAR CLIENTE
        CadastroClienteDto cliente = cadastrarCliente();

        //BUSCAR CLIENTE CADASTRADO PELO ID
        given()
                .accept("application/json")
                .log().all()
                .when()
                .get("/clientes/" + cliente.idCliente)
                .then()
                .log().all()
                .statusCode(200)
                .body("contato", equalTo(cliente.contato))
                .body("email", equalTo(cliente.email))
                .body("telefone", equalTo(cliente.telefone))
                .body("documento", equalTo(cliente.documento))
                .body("tipoPessoa", equalTo(cliente.tipoPessoa))
                .body("nomeEmpresa", equalTo(cliente.nomeEmpresa))
                .body("status", equalTo(cliente.status))
                .body("endereco", notNullValue());
    }

    @Test
    public void deveAtualizarCliente() {
        //CADASTRAR CLIENTE
        CadastroClienteDto cliente = cadastrarCliente();

        //ATUALIZAR CLIENTE
        String contatoAtualizado = "user-test-updated";
        String emailAtualizado = "user-test-updated@email.com";
        String telefoneAtualizado = "(11) 91234-5678";
        String documentoAtualizado = "123.456.789-00";
        String tipoPessoaAtualizado = "FISICA";
        String nomeEmpresaAtualizado = "Company Test Updated";
        String statusAtualizado = "ATIVO";

        String jsonAtualizado = clienteJson().formatted(contatoAtualizado, emailAtualizado, telefoneAtualizado, documentoAtualizado, tipoPessoaAtualizado, nomeEmpresaAtualizado, statusAtualizado);

        given()
                .contentType("application/json")
                .body(jsonAtualizado)
                .log().all()
                .when()
                .put("/clientes/" + cliente.idCliente)
                .then()
                .log().all()
                .statusCode(200)
                .body("contato", equalTo(contatoAtualizado))
                .body("email", equalTo(emailAtualizado))
                .body("telefone", equalTo(telefoneAtualizado))
                .body("documento", equalTo(documentoAtualizado))
                .body("tipoPessoa", equalTo(tipoPessoaAtualizado))
                .body("nomeEmpresa", equalTo(nomeEmpresaAtualizado))
                .body("status", equalTo(statusAtualizado))
                .body("endereco", notNullValue());
    }

    @Test
    public void deveAtualizarParcialmenteCliente() {
        //CADASTRAR CLIENTE
        CadastroClienteDto cliente = cadastrarCliente();

        //ATUALIZAR CLIENTE PARCIALMENTE
        String contatoAtualizado = "user-test-updated";
        String emailAtualizado = "user-test-updated@email.com";
        String statusAtualizado = "ATIVO";

        String jsonAtualizado = """
                    {
                      "contato": "%s",
                      "email": "%s",
                      "status": "%s"
                    }
                """.formatted(contatoAtualizado, emailAtualizado, statusAtualizado);

        given()
                .contentType("application/json")
                .body(jsonAtualizado)
                .log().all()
                .when()
                .patch("/clientes/" + cliente.idCliente)
                .then()
                .log().all()
                .statusCode(200)
                .body("contato", equalTo(contatoAtualizado))
                .body("email", equalTo(emailAtualizado))
                .body("telefone", equalTo(cliente.telefone))
                .body("documento", equalTo(cliente.documento))
                .body("tipoPessoa", equalTo(cliente.tipoPessoa))
                .body("nomeEmpresa", equalTo(cliente.nomeEmpresa))
                .body("status", equalTo(statusAtualizado))
                .body("endereco", notNullValue());
    }

    @Test
    public void deveDeletarClientePeloId() {
        //CADASTRAR CLIENTE
        CadastroClienteDto cliente = cadastrarCliente();

        //ATUALIZAR STATUS
        String json = """
                        {
                        "status": "INATIVO"
                    }
                """;

        given()
                .body(json)
                .contentType("application/json")
                .when()
                .patch("/clientes/" + cliente.idCliente);


        //DELETAR CLIENTE
        given()
                .log().all()
                .when()
                .delete("/clientes/" + cliente.idCliente)
                .then()
                .log().all()
                .statusCode(204);

    }


}
