package com.catapi.votes;


import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;




public class VotesTest {

    // Variáveis globais
    public String contentType = "application/json";
    public String idVote;

    @BeforeEach
    public void setup() {
        baseURI = "https://api.thecatapi.com";
        basePath = "/v1";

    }



//////////////////////// CENÁRIOS PARA O POST ///////////////////////////

    @Test
    //testDadoUmUsuarioQuandoCadastraVoteEntaoObtenhoStatusCode201
    public void efetuarVotacao() {

        Response response =
        given()
                .log().all()
                .contentType(contentType)
                .header("x-api-key", "DEMO-API-KEY")
                .body("{\"image_id\":\"asf2\",\"sub_id\":\"user01\",\"value\":1}")
        .when()
                .post("/votes");
        response.then()
                .log().all()
                .header("content-type", "application/json; charset=utf-8")
                .body("message" , equalTo("SUCCESS"))
                .body("id" , notNullValue())
                .body("image_id" , equalTo("asf2"))
                .body("sub_id", equalTo("user01"))
                .body("value" , equalTo(1))
                .body("country_code" , equalTo("BR"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/post-votes.json"))

                .statusCode(201);

                idVote = response.jsonPath().getString("id");
                System.out.println("ID retorno:" + idVote);


    }

    @Test //testDadoUmUsuarioQuandoCadastraVoteComValoresValidosEntaoObtenhoStatusCode201
    public void efetuarVotacaoValoresValidos() {
        for (int valor = 1; valor <= 10; valor++) {

        Response response =
        given()
                .contentType(contentType)
                .header("x-api-key", "DEMO-API-KEY")
                .body("{\"image_id\":\"asf2\",\"sub_id\":\"sense2024.2\",\"value\":" + valor + "}")
        .when()
                .post("/votes");
        response.then()
                .log().all()
                .statusCode(201);

                idVote = response.jsonPath().getString("id");
                System.out.println("ID retorno:" + idVote);

            }
    }


    @Test //testDadoUmUsuarioQuandoCadastraVotePassandoUmValorDoVotoAcimaDe10EntaoObtenhoStatusCode400
    public void efetuarVotacaoComValorNaoValido() {

         given()
                .contentType(contentType)
                .header("x-api-key", "DEMO-API-KEY")
                .body("{\"image_id\":\"asf2\",\"sub_id\":\"sense2024.2\",\"value\":11}")
        .when()
                .post("/votes")
        .then()
                .log().all()
                .header("content-type", "application/json; charset=utf-8")
                .statusCode(400);
                            

    }


    @Test //testDadoUmUsuarioQuandoCadastraVotePassandoUmValorDoVotoNegativoEntaoObtenhoStatusCode400
    public void efetuaVotacaoValorNegativo() {

         given()
                .contentType(contentType)
                .header("x-api-key", "DEMO-API-KEY")
                .body("{\"image_id\":\"asf2\",\"sub_id\":\"sense2024.2\",\"value\":-1}")
        .when()
                .post("/votes")
        .then()
                .log().all()
                .header("content-type", "application/json; charset=utf-8")
                .statusCode(400);

    }

    ///////////////////////// CENÁRIOS PARA O GET ALL ///////////////////////


    @Test //testDadoQueDesejoBuscarTodosVotosQuandoNaoPassoNenhumFiltroEFacoBuscaEntaoObtenhoStatusCode200ETodosVotos
    public void buscaGeral() {
        given()
                .contentType(contentType)
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
                .body("created_at", hasItems())
                .body("value",  hasItems())
                .body("country_code" , hasItems())
                .body("image.id" , hasItems())
                .body("image.url" , hasItems())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get-votes.json"))
                .statusCode(200);

    }

    @Test //testDadoQueDesejoBuscarTodosVotosOrdemCrescenteQuandoInformoFiltroASCEntaoObtenhoStatusCode200ETodosVotosDecrescente
    public void buscaOrdemCrescente() {
        Response response =
                given()
                        .contentType(contentType)
                        .header("x-api-key", "DEMO-API-KEY")
                .when()
                        .get("/votes?order=ASC")
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
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get-votes.json"))
                        .extract()
                        .response();

        /// Extrair os valores do "created_at" do response body ///
        List<String> createdDates = response.jsonPath().getList("created_at");

        /// Verifica se os valores estão em ordem crescente ///
        for (int i = 1; i < createdDates.size(); i++) {
            Assert.assertTrue(createdDates.get(i - 1).compareTo(createdDates.get(i)) <= 0,
                    "Os valores de created_at não estão em ordem crescente");
        }




    }


    @Test //testDadoQueDesejoBuscarTodosVotosOrdemDecrescenteQuandoInformoFiltroDESCEntaoObtenhoStatusCode200ETodosVotosDecrescente
    public void buscaOrdemDecrescente() {
        Response response =
            given()
                .contentType(contentType)
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
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get-votes.json"))
                .extract()
                .response();

        /// Extrair os valores do "created_at" do response body ///
        List<String> createdDates = response.jsonPath().getList("created_at");

        /// Verifica se os valores estão em ordem decrescente ///
        for (int i = 1; i < createdDates.size(); i++) {
            Assert.assertTrue(createdDates.get(i - 1).compareTo(createdDates.get(i)) >= 0,
                    "Os valores de created_at não estão em ordem decrescente");
        }

    }



    @Test //testDadoQueNaoInformoKeyEntaoObtenhoStatusCode401
    public void buscaGetAllSemKey() {
        given()
                .header("content-type","application/json")

        .when()
                .get("/votes")
        .then()
                .statusCode(401);

    }


    @Test //testDadoUmUsuarioQuandoBuscaUmDeterminadoVotoPeloSubIDEntaoObtenhoStatusCode200EDadosDaqueleVoto
    public void buscaPorSubID() {
        given()
                .contentType(contentType)
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
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get-votes.json"))
                .statusCode(200);

    }




    //////////////////////// CENÁRIOS PARA O GET BY ID ////////////////////

    @Test //testParaBuscarUmVotoDadoQueNaoInformoKeyEntaoObtenhoStatusCode401
    public void buscaGetIDSemKey() {
        given()
                .header("content-type", "application/json")

        .when()
                .get("/votes/" + idVote)
        .then()
                .statusCode(401);

    }



    @Test //testDadoUmUsuarioQuandoBuscaUmDeterminadoVotoPeloIDEntaoObtenhoStatusCode200EDadosDaqueleVoto
    public void buscaPeloIdVote() {
        efetuarVotacao();

        given()
                .contentType(contentType)
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

    }

    @Test //testQuandoBuscOUmDeterminadoVotoPassandoUmIdNaoExistenteEntaoObtenhoStatusCode404
    public void buscaVotoComIdInexistente() {
        given()
                .contentType(contentType)
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .log().all()
                .get("/votes/" + "11111111")
        .then()
                .log().all()
                .header("content-type", "text/plain; charset=utf-8")
                .statusCode(404);

    }

        //////////////////////// CENÁRIOS PARA O DELETE ////////////////////

        @Test //testDadoQueDesejoApagarUmVotoENaoInformoKeyEntaoObtenhoStatusCode401
        public void apagarSemKey() {
                given()
                        .header("content-type", "application/json")

                .when()
                        .delete("/votes/" + idVote)
                .then()
                        .statusCode(401);

        }
        
        

    @Test //testDadoUmUsuarioQuandoDesejaApagarOVotoEntaoObtenhoStatusCode200()
    public void apagaVoto() {
        efetuarVotacao();
        given()
                .contentType(contentType)
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .delete("/votes/" + idVote)
        .then()
                .log().all()
                .header("content-type", "application/json; charset=utf-8")
                .body("message" , equalTo("SUCCESS"))
                .statusCode(200);

    }


    @Test //testDadoUmUsuarioQuandoDesejaApagarUmVotoNaoExistenteEntaoObtenhoStatusCode404
    public void apagarVotoNaoExistente() {

        given()
                .contentType(contentType)
                .header("x-api-key", "DEMO-API-KEY")
        .when()
                .delete("/votes/" + 28885)
        .then()
                .log().all()
                .header("content-type", "text/plain; charset=utf-8")
                .statusCode(404);

    }


}
