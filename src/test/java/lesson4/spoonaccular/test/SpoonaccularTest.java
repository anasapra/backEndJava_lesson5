package lesson4.spoonaccular.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import lesson4.spoonaccular.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class SpoonaccularTest extends AbstractTest {
    private static final String API_KEY = "801c235e0d9a4f2fbcc2d9c11e9cf89c";
    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = "https://api.spoonacular.com/";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }
    @AfterEach
    void tearDown() {
        given()
                .then()
                .statusCode(200);
    }
}
