package api.pet;

import base.NewPet;
import base.NewUser;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
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

    ///////////////////////////////////////////////////////////////////////////////////
    // ЗАДАЧА 1
    @Test
    @Feature("Создать коллекцию питомцев и проверить из в наличии")
    @Description("Add a new pet to the store POST https://petstore.swagger.io/v2/pet")
    public void createNewPetCollection() {

        HashMap<Integer, NewPet> idAndPet = new HashMap<>();
        for (int i = 0; i <= 10; i++) {
            int id = 101010100 + i;
            NewPet pet = new NewPet(
                    id,
                    new NewPet.Category(0, "Dogs"),
                    "doggie",
                    List.of("https://example.com/dog.jpg"),
                    List.of(new NewPet.Tag(0, "fluffy")),
                    "available"
            );

            // Когда сформирована коллекция животных
            idAndPet.put(id, pet);

            // Тогда животное может быть заведено в базу
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

            // Тогда можно проверить наличие животного в базе
            Response getResponse =
                    given()
                            .header("Content-type", "application/json")
                            .when()
                            .get("/pet/" + id);
            getResponse.then().assertThat().body("id", notNullValue())
                    .and()
                    .statusCode(200);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // ЗАДАЧА 2

    @Test
    @Feature("Пересоздать коллекцию питомцев с противоположным статусом")
    @Description("Add a new pet to the store POST https://petstore.swagger.io/v2/pet")
    public void putNewPetCollectionWithStatus() {

        NewPet[] petAvailable = new NewPet[2];
        NewPet[] petUnAvailable = new NewPet[2];

        for (int i = 0; i < 2; i++) {
            int id = 192929299 + i;
            NewPet pet = new NewPet(
                    id,
                    new NewPet.Category(0, "Dogs"),
                    "doggie",
                    List.of("https://example.com/dog.jpg"),
                    List.of(new NewPet.Tag(0, "fluffy")),
                    "available"
            );
            petAvailable[i] = pet;
        }
        for (int i = 0; i < 2; i++) {
            int id = 492929299 + i;
            NewPet pet = new NewPet(
                    id,
                    new NewPet.Category(0, "Dogs"),
                    "doggie",
                    List.of("https://example.com/dog.jpg"),
                    List.of(new NewPet.Tag(0, "fluffy")),
                    "notavailable"
            );
            petUnAvailable[i] = pet;
        }

        // Когда сформирована коллекция животных с ключом по статусу
        HashMap<String, List<NewPet>> statusAndPet = new HashMap<>();
        statusAndPet.put("notavailable", Arrays.stream(petUnAvailable).toList());
        statusAndPet.put("available", Arrays.stream(petAvailable).toList());

        for (int i = 0; i < 2; i++) {
            NewPet petNotAv= statusAndPet.get("notavailable").get(0);
            NewPet petAv = statusAndPet.get("available").get(0);
            // Тогда животное может быть заведено в базу
            Response responseAv =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(petAv)
                            .when()
                            .post("/pet");
            responseAv.then().assertThat().body("id", notNullValue())
                    .and()
                    .statusCode(200);

            Response responseNot =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(petNotAv)
                            .when()
                            .post("/pet");
            responseNot.then().assertThat().body("id", notNullValue())
                    .and()
                    .statusCode(200);
        }

        NewPet[] petAvailableNew = new NewPet[2];
        NewPet[] petUnAvailableNew = new NewPet[2];

        for (int i = 0; i < 2; i++) {
            int id = 192929299 + i;
            NewPet pet = new NewPet(
                    id,
                    new NewPet.Category(0, "Dogs"),
                    "doggie",
                    List.of("https://example.com/dog.jpg"),
                    List.of(new NewPet.Tag(0, "fluffy")),
                    "notavailable"
            );
            petUnAvailableNew[i] = pet;
        }
        for (int i = 0; i < 2; i++) {
            int id = 492929299 + i;
            NewPet pet = new NewPet(
                    id,
                    new NewPet.Category(0, "Dogs"),
                    "doggie",
                    List.of("https://example.com/dog.jpg"),
                    List.of(new NewPet.Tag(0, "fluffy")),
                    "available"
            );
            petAvailableNew[i] = pet;
        }

        // Когда сформирована новая коллекция животных с ключом по противоположному статусу
        HashMap<String, List<NewPet>> statusAndPetNew = new HashMap<>();
        statusAndPetNew.put("notavailable", Arrays.stream(petUnAvailableNew).toList());
        statusAndPetNew.put("available", Arrays.stream(petAvailableNew).toList());

        for (int i = 0; i < 2; i++) {
            NewPet petNotAv= statusAndPetNew.get("notavailable").get(0);
            NewPet petAv = statusAndPetNew.get("available").get(0);
            // Тогда животное может быть обновлено в базе
            Response responseAv =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(petAv)
                            .when()
                            .put("/pet");
            responseAv.then().assertThat().body("id", notNullValue())
                    .and()
                    .statusCode(200);

            Response responseNot =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(petNotAv)
                            .when()
                            .post("/pet");
            responseNot.then().assertThat().body("id", notNullValue())
                    .and()
                    .statusCode(200);
        }
    }
}
