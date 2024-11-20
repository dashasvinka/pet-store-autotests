package pet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class HelperApiTest {
    
    // В данном классе подготовительные выборочные степы для API Test //

//    public void Record createPetPrecondition(test Record) {
//        Response response =
//                given()
//                        .header("Content-type", "application/json")
//                        .and()
//                        .body(test)
//                        .when()
//                        .post("/pet");
//        response.then().assertThat().body("id", notNullValue())
//                .and()
//                .statusCode(200);
//        NewPet newPet = response.as(NewPet.class);
//        return newPet;
//    }
}
