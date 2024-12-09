package api.pet;

import base.NewPet;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class BaseApiPetTest {

    // В данном классе степы для API Test //

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Step("Создание питомца через API")
    public static NewPet createPetPrecondition(NewPet pet) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(pet)
                        .when()
                        .post("/pet");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        return response.as(NewPet.class);
    }
}

