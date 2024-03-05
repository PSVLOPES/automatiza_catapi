package com.catapi.votes;



import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;



public class VotesTest {

    // Variável global para armazenar o ID do voto
    private static int idVote;


    @BeforeClass
    public static void setup() {
        baseURI = "https://api.thecatapi.com";
        basePath = "/v1";



    }



//////////////////////// CENÁRIOS PARA O POST ///////////////////////////

    @Test
    public void test01DadoUmUsuarioQuandoCadastraVoteEntaoObtenhoStatusCode201() {
        String requestBody = "{\"image_id\":\"asf2\",\"sub_id\":\"user01\",\"value\":1}";

        idVote = given()
                .contentType("application/json")
                .header("x-api-key", "DEMO-API-KEY")
                .body(requestBody)
        .when()
                .post("/votes")
        .then()
                .log().all()
                .header("content-type", "application/json; charset=utf-8")
                .body("message" , equalTo("SUCCESS"))
                .body("id" , notNullValue())
                .body("image_id" , equalTo("asf2"))
                .body("sub_id", equalTo("sense2024.2"))
                .body("value" , equalTo(1))
                .body("country_code" , equalTo("BR"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("post-votes.json"))
                .statusCode(201)
                .extract()
                .path("id");


    }

    @Test
    public void testDadoUmUsuarioQuandoCadastraVoteComValoresValidosEntaoObtenhoStatusCode201() {
            
            for (int valor = 1; valor <= 10; valor++) {
                    
                    String requestBody = "{\"image_id\":\"asf2\",\"sub_id\":\"sense2024.2\",\"value\":" + valor + "}";

                    
                        given()
                                    .contentType("application/json")
                                    .header("x-api-key", "DEMO-API-KEY")
                                    .body(requestBody)
                        .when()
                                    .post("/votes")
                        .then()
                                    .log().all()
                                    .statusCode(201);
            }
    }


    @Test
    public void testDadoUmUsuarioQuandoCadastraVotePassandoUmValorDoVotoAcimaDe10EntaoObtenhoStatusCode400() {
            String requestBody = "{\"image_id\":\"asf2\",\"sub_id\":\"sense2024.2\",\"value\":11}";

                 given()
                            .contentType("application/json")
                            .header("x-api-key", "DEMO-API-KEY")
                            .body(requestBody)
                .when()
                            .post("/votes")
                .then()
                            .log().all()
                            .header("content-type", "application/json; charset=utf-8")
                            .statusCode(400);
                            

    }


    @Test
    public void testDadoUmUsuarioQuandoCadastraVotePassandoUmValorDoVotoNegativoEntaoObtenhoStatusCode400() {
            String requestBody = "{\"image_id\":\"asf2\",\"sub_id\":\"sense2024.2\",\"value\":-1}";

                 given()
                            .contentType("application/json")
                            .header("x-api-key", "DEMO-API-KEY")
                            .body(requestBody)
                .when()
                            .post("/votes")
                .then()
                            .log().all()
                            .header("content-type", "application/json; charset=utf-8")
                            .statusCode(400);

    }

    ///////////////////////// CENÁRIOS PARA O GET ALL ///////////////////////


    @Test
    public void testDadoQueDesejoBuscarTodosVotosQuandoNaoPassoNenhumFiltroEFacoBuscaEntaoObtenhoStatusCode200ETodosVotos() {
        given()
                .contentType("application/json")
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .get("/votes")
        .then()
                .header("x-dns-prefetch-control", equalTo("off"))
                .header("x-frame-options", equalTo("SAMEORIGIN"))
                .header("strict-transport-security", equalTo("max-age=15552000; includeSubDomains"))
                .header("x-download-options", equalTo("noopen"))
                .header("x-content-type-options", equalTo("nosniff"))
                .header("x-xss-protection", equalTo("1; mode=block"))
                .header("vary", equalTo("Origin"))
                .header("content-type", equalTo("application/json"))
                .header("pagination-page", equalTo("0"))
                .header("pagination-limit", equalTo("100"))
                .header("pagination-count", hasItems())
                .body("id" , hasItems())
                .body("image_id" , hasItems())
                .body("sub_id" , hasItems())
                .body("created_at" , hasItems())
                .body("value",  hasItems())
                .body("country_code" , hasItems())
                .body("image.id" , hasItems())
                .body("image.url" , hasItems())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("get-votes.json"))
                .statusCode(200);

    };

    @Test
    public void testDadoQueDesejoBuscarTodosVotosOrdemDecrescenteQuandoInformoFiltroDESCEntaoObtenhoStatusCode200ETodosVotosDecrescente() {
        given()
                .contentType("application/json")
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .get("/votes?order=DESC")
        .then()
                .header("x-dns-prefetch-control", equalTo("off"))
                .header("x-frame-options", equalTo("SAMEORIGIN"))
                .header("strict-transport-security", equalTo("max-age=15552000; includeSubDomains"))
                .header("x-download-options", equalTo("noopen"))
                .header("x-content-type-options", equalTo("nosniff"))
                .header("x-xss-protection", equalTo("1; mode=block"))
                .header("vary", equalTo("Origin"))
                .header("content-type", equalTo("application/json"))
                .header("pagination-page", equalTo("0"))
                .header("pagination-limit", equalTo("100"))
                .header("pagination-count", hasItems())
                .body("id" , hasItems())
                .body("image_id" , hasItems())
                .body("sub_id" , hasItems())
                .body("created_at" , hasItems())
                .body("value",  hasItems())
                .body("country_code" , hasItems())
                .body("image.id" , hasItems())
                .body("image.url" , hasItems())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("get-votes.json"))
                .statusCode(200);

    };

    @Test
    public void testDadoQueNaoInformoKeyEntaoObtenhoStatusCode401() {
        given()
                .header("content-type","application/json")

        .when()
                .get("/votes")
        .then()
                .statusCode(401);

    };

    @Test
    public void testDadoUmUsuarioQuandoBuscaUmDeterminadoVotoPeloSubIDEntaoObtenhoStatusCode200EDadosDaqueleVoto() {
        given()
                .contentType("application/json")
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .get("/votes?sub_id=sense2024")
        .then()
                .header("x-dns-prefetch-control", equalTo("off"))
                .header("x-frame-options", equalTo("SAMEORIGIN"))
                .header("strict-transport-security", equalTo("max-age=15552000; includeSubDomains"))
                .header("x-download-options", equalTo("noopen"))
                .header("x-content-type-options", equalTo("nosniff"))
                .header("x-xss-protection", equalTo("1; mode=block"))
                .header("vary", equalTo("Origin"))
                .header("content-type", equalTo("application/json"))
                .header("pagination-page", equalTo("0"))
                .header("pagination-limit", equalTo("100"))
                .header("pagination-count", hasItems())
                .body("id" , hasItems())
                .body("image_id" , hasItems())
                .body("sub_id" , hasItems())
                .body("created_at" , hasItems())
                .body("value",  hasItems())
                .body("country_code" , hasItems())
                .body("image.id" , hasItems())
                .body("image.url" , hasItems())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("get-votes.json"))
                .statusCode(200);

    };




    //////////////////////// CENÁRIOS PARA O GET BY ID ////////////////////

    @Test
    public void testDadoUmUsuarioQuandoBuscaUmDeterminadoVotoPeloIDEntaoObtenhoStatusCode200EDadosDaqueleVoto() {
        given()
                .contentType("application/json")
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .log().all()
                .get("/votes/" + idVote)
        .then()
                .log().all()
                .header("content-type", "application/json; charset=utf-8")
                .body("id" , hasItems())
                .body("image_id" , hasItems())
                .body("sub_id" , hasItems())
                .body("created_at" , hasItems())
                .body("value",  hasItems())
                .body("country_code" , hasItems())
                .body("image.id" , hasItems())
                .body("image.url" , hasItems())
                .statusCode(200);

    };

    @Test
    public void testQuandoBuscOUmDeterminadoVotoPassandoUmIdNaoExistenteEntaoObtenhoStatusCode404() {
        given()
                .contentType("application/json")
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .log().all()
                .get("/votes/" + "11111111")
        .then()
                .log().all()
                .header("content-type", "text/plain; charset=utf-8")
                .statusCode(404);

    };






}
