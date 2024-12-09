package api.pet;

import base.NewPet;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("Функциональность для взаимодействия с сущностью {питомец}")
@Epic("Основная функциональность - позитивные сценарии")
@Story("https://docs.google.com/spreadsheets/d/1uGjFS-slTLruBczZY_8w7I5jqQ_7hMcCt-947USjzZU/edit?usp=sharing")
@DisplayName("Тесты на функциональность питомцев")

public class PositivePetTests extends BaseApiPetTest {

    @Test
    @Feature("Создать питомца")
    @Description("Add a new pet to the store POST https://petstore.swagger.io/v2/pet")
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
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(createdPet.id()).isNotNull();
            softAssertions.assertThat(createdPet.status()).isEqualTo("available");
            softAssertions.assertThat(createdPet.name()).isEqualTo("doggie");
            softAssertions.assertThat(createdPet.photoUrls().size()).isEqualTo(1);
            softAssertions.assertThat(createdPet.photoUrls().get(0)).isEqualTo("https://example.com/dog.jpg");
            softAssertions.assertThat(createdPet.category()).isEqualTo(new NewPet.Category(0, "Dogs"));
            softAssertions.assertThat(createdPet.tags().size()).isEqualTo(1);
            softAssertions.assertThat(createdPet.tags().get(0)).isEqualTo(new NewPet.Tag(0, "fluffy"));
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @Test
    @Feature("Изменить питомца")
    @Description("Update an existing pet PUT https://petstore.swagger.io/v2/pet")
    public void updatePet() {
        NewPet pet = new NewPet(
                0,
                new NewPet.Category(0, "Dogs"),
                "doggie",
                List.of("https://example.com/dog.jpg"),
                List.of(new NewPet.Tag(0, "fluffy")),
                "available"
        );

        NewPet petPet = createPetPrecondition(pet);
        Double idPet = petPet.id();
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
        responseSecond.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        NewPet updatedPet = responseSecond.as(NewPet.class);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updatedPet.id()).isNotNull();
            softAssertions.assertThat(updatedPet.id()).isEqualTo(idPet);
            softAssertions.assertThat(updatedPet.status()).isEqualTo("available");
            softAssertions.assertThat(updatedPet.name()).isEqualTo("doggy");
            softAssertions.assertThat(updatedPet.photoUrls().size()).isEqualTo(1);
            softAssertions.assertThat(updatedPet.photoUrls().get(0)).isEqualTo("https://example.com/dog.jpg");
            softAssertions.assertThat(updatedPet.category()).isEqualTo(new NewPet.Category(0, "My pet"));
            softAssertions.assertThat(updatedPet.tags().size()).isEqualTo(1);
            softAssertions.assertThat(updatedPet.tags().get(0)).isEqualTo(new NewPet.Tag(0, "kus-kus"));
        });
    }
}
