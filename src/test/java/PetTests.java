import base.NewPet;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Feature("Функциональность для взаимодействия с сущностью {питомец}")
@Epic("Основная функциональность")
@Story("https://docs.google.com/spreadsheets/d/1uGjFS-slTLruBczZY_8w7I5jqQ_7hMcCt-947USjzZU/edit?usp=sharing")
@DisplayName("Тесты на функциональность питомцев")
public class PetTests extends BaseApiTest {

    // POST https://petstore.swagger.io/v2/pet
    // Add a new pet to the store
    @Test
    public void createNewPet() {
        NewPet pet = new NewPet(
                0,
                new NewPet.Category(0, "Dogs"),
                "doggie",
                List.of("https://example.com/dog.jpg"),
                List.of(new NewPet.Tag(0, "fluffy")),
                "available"
        );
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
        NewPet createdPet = response.as(NewPet.class);
        assertNotNull(createdPet.id());
        assertEquals(createdPet.status(), "available");
        assertEquals(createdPet.name(), "doggie");
        assertEquals(createdPet.photoUrls().size(), 1);
        assertEquals(createdPet.photoUrls().get(0), "https://example.com/dog.jpg");
        assertEquals(createdPet.category(), new NewPet.Category(0, "Dogs"));
        assertEquals(createdPet.tags().size(), 1);
        assertEquals(createdPet.tags().get(0), new NewPet.Tag(0, "fluffy"));
    }

    ///////////////////////////////////////////////////////////////////////////////////

    // PUT https://petstore.swagger.io/v2/pet
    // Update an existing pet
    @Test
    public void updatePet() {
        NewPet pet = new NewPet(
                0,
                new NewPet.Category(0, "Dogs"),
                "doggie",
                List.of("https://example.com/dog.jpg"),
                List.of(new NewPet.Tag(0, "fluffy")),
                "available"
        );
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
        NewPet createdPet = response.as(NewPet.class);
        Double idPet = createdPet.id();
        NewPet petToUpdate = new NewPet(
                idPet,
                new NewPet.Category(0, "My pet"),
                "doggy",
                List.of("https://example.com/dog.jpg"),
                List.of(new NewPet.Tag(0, "kus-kus")),
                "available"
        );
        Response responseSecond =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(petToUpdate)
                        .when()
                        .put("/pet");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        NewPet updatedPet = responseSecond.as(NewPet.class);
        assertNotNull(updatedPet.id());
        assertEquals(updatedPet.id(), idPet);
        assertEquals(updatedPet.status(), "available");
        assertEquals(updatedPet.name(), "doggy");
        assertEquals(updatedPet.photoUrls().size(), 1);
        assertEquals(updatedPet.photoUrls().get(0), "https://example.com/dog.jpg");
        assertEquals(updatedPet.category(), new NewPet.Category(0, "My pet"));
        assertEquals(updatedPet.tags().size(), 1);
        assertEquals(updatedPet.tags().get(0), new NewPet.Tag(0, "kus-kus"));
    }
}
