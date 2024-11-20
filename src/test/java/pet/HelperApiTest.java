package pet;

import base.NewPet;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class HelperApiTest {
    
    // В данном классе подготовительные выборочные степы для API Test //

    public NewPet createPetPrecondition(NewPet pet) {
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
