package pet;

import base.BaseError;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;

@Feature("Функциональность для взаимодействия с сущностью {питомец}")
@Epic("Основная функциональность - негативные сценарии")
@Story("https://docs.google.com/spreadsheets/d/1uGjFS-slTLruBczZY_8w7I5jqQ_7hMcCt-947USjzZU/edit?usp=sharing")
@org.junit.jupiter.api.DisplayName("Тесты на функциональность питомцев")
@ExtendWith(AllureJunit5.class)
public class NegativePetTests extends BaseApiTest {

    @org.junit.jupiter.api.Test
    @Feature("Запросить несуществующего питомца")
    @Description("Find pet by ID GET/pet/{petId} https://petstore.swagger.io/v2/pet")
    public void getInvalidPet() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get("/pet/2345324642363203040103413");
        response.then()
                .statusCode(404);
        BaseError errorResponse = response.as(BaseError.class);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(errorResponse.code()).isEqualTo(404);
            softAssertions.assertThat(errorResponse.type()).isEqualTo("unknown");
            softAssertions.assertThat(errorResponse.message()).isEqualTo("java.lang.NumberFormatException: For input string: \"2345324642363203040103413\""
            );
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @org.junit.jupiter.api.Test
    @Feature("Удалить несуществующего питомца")
    @Description("Deletes a pet DELETE /pet/{petId} https://petstore.swagger.io/v2/pet")
    public void deleteInvalidPet() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete("/pet/2345324642363203040103413");
        response.then()
                .statusCode(404);
        BaseError errorResponse = response.as(BaseError.class);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(errorResponse.code()).isEqualTo(404);
            softAssertions.assertThat(errorResponse.type()).isEqualTo("unknown");
            softAssertions.assertThat(errorResponse.message()).isEqualTo("java.lang.NumberFormatException: For input string: \"2345324642363203040103413\""
            );
        });
    }
}
