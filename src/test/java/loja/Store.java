package loja;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class Store {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
}

    @Test
    public void ordenarExecucaoStore() throws IOException {
        venderPet();
        consultarPedido();
        excluirPedido();
    }

    @Test
    public void venderPet() throws IOException {
        String jsonBody = lerJson("data/order.json");

        given()
            .log().all()
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("https://petstore.swagger.io/v2/store/order")

        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(10))
            .body("status", is("placed"))
            .body("complete", is(true))

        ;
    }

    // Reach or Research / Consultar / Get
    @Test
    public void consultarPedido() {
        String orderId = "10";

        given()
            .contentType("application/json")
            .log().all()
        .when()
            .get("https://petstore.swagger.io/v2/store/order/" + orderId)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(10))
            .body("status", is("placed"))
            .body("complete", is(true))
        ;

    }

    // Delete / Excluir / Delete
    @Test
    public void excluirPedido() {
        String orderId = "10";

        given()
            .contentType("application/json")
            .log().all()
        .when()
            .delete("https://petstore.swagger.io/v2/store/order/" + orderId)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", Matchers.is(200))
            .body("message", Matchers.is(orderId))

        ;

    }
}
