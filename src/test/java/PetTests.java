import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("Функциональность для взаимодействия с сущностью {питомец}")
@Epic("Основная функциональность")
@Story("https://docs.google.com/spreadsheets/d/1uGjFS-slTLruBczZY_8w7I5jqQ_7hMcCt-947USjzZU/edit?usp=sharing")
@DisplayName("Тесты на функциональность питомцев")
public class PetTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }


    // POST https://petstore.swagger.io/v2/pet
    // Add a new pet to the store
    @Test
    public void createNewPet() {
        File json = new File("/Users/ruakhdt/Downloads/pet-store-autotests/src/test/resources/newPet");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/pet");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }
}
