package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.is;

public class User {


    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));

    }

    @Test
    public void ordenarExecucao() throws IOException {
        incluirUser();
        consultarUser();
        alterarUser();
        excluirUser();
    }


    // Create / Incluir / POST
    @Test
    public void incluirUser() throws IOException {
        String jsonBody = lerJson("data/user.json");

        given()
            .contentType("application/json")
            .log().all()
            .body(jsonBody)
        .when()
            .post("https://petstore.swagger.io/v2/user")
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is("5313"))
        ;

        System.out.print("Executou o serviço");

    }

    // Reach or Research / Consultar / Get
    @Test
    public void consultarUser() {
        String username = "marilsab";

        given()
            .contentType("application/json")
            .log().all()
        .when()
            .get("https://petstore.swagger.io/v2/user/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(5313))
            .body("firstName", is("Marilsa"))
            .body("lastName", is("Barros da Silva"))
            .body("email", is("marilsa@teste.com"))
        ;

    }

    // Update / Alterar / Put
    @Test
    public void alterarUser() throws IOException {
        // Ler o conteúdo do arquivo userput.json
        String jsonBody = lerJson("data/userput.json");
        String username = "marilsab";

        given()
            .contentType("application/json")
            .log().all()
            .body(jsonBody)
        .when()
            .put("https://petstore.swagger.io/v2/user/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is("5313"))
        ;

    }

    // Delete / Excluir / Delete
    @Test
    public void excluirUser() {
        String username = "marilsab";

        given()
            .contentType("application/json")
            .log().all()
        .when()
            .delete("https://petstore.swagger.io/v2/user/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("message", is(username))

        ;

    }
}
