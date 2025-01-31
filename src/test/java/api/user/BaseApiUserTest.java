package api.user;

import base.NewUser;
import base.StatusResponse;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public class BaseApiUserTest {

    // В данном классе степы для API-test сущности юзер //

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Step("Сформирован объекта пользователя")
    public static NewUser createUserObjPrecondition(int id, String username, String firstName,String lastName,
                                                    String email,String password,String phone,int status) {
        return NewUser.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(status)
                .build();
    }

    @Step("Создан новый пользователь и проверен успешный статус")
    public static StatusResponse createNewUserSuccessPrecondition(NewUser user) {
        Response response = postNewUser(user);
        response.then()
                .assertThat()
                .statusCode(200);
        return response.as(StatusResponse.class);
    }

    @Step("Создана коллекция пользователей и проверен успешный статус")
    public static StatusResponse createNewUserCollectionSuccess(NewUser[] users) {
        Response response = postNewUsersList(users);
        response.then()
                .assertThat()
                .statusCode(200);
        return response.as(StatusResponse.class);
    }

    @Step("Проверено существование пользователя по его имени")
    public static NewUser checkUserIsExistByNameSuccess(String userName) {
        Response response = getUserByName(userName);
        response.then()
                .assertThat()
                .statusCode(200);
        return response.as(NewUser.class);
    }

    @Step("Отправили запрос POST/user")
    public static Response postNewUser(NewUser user) {
         return given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post("/user");
    }

    @Step("Отправили запрос POST/user")
    public static Response postNewUsersList(NewUser[] users) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(users)
                .when()
                .post("/user/createWithList");
    }

    @Step("Отправили запрос GET/user")
    public static Response getUserByName(String userName) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/user/"+userName);
    }

    @Step("Проверить результат создания пользователя")
    public void checkNewUserStatus(String message, int actualCode, int expectedCode,
                                                    String actualType, String expectedType) {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(message).isNotNull();
            softAssertions.assertThat(actualCode).isEqualTo(expectedCode);
            softAssertions.assertThat(actualType).isEqualTo(expectedType);
        });
    }
}
