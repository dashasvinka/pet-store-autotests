package api.user;

import base.NewUser;
import base.StatusResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class PositiveUserTests extends BaseApiUserTest {

    @Test
    @Feature("Создать пользователя")
    @Description("POST/user Create user https://petstore.swagger.io/v2/pet")
    public void createNewUser() {

        // Вариант создания 1 - объект
        NewUser user = createUserObjPrecondition(
                0,
                "testuser",
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                "123-456-7890",
                0
        );

        // Вариант создания 2 - билдер
        NewUser user1 = NewUser.createNewUser()
                .email("")
                .username("")
                .firstName("")
                .lastName("")
                .email("")
                .password("")
                .phone("")
                .build();


        StatusResponse response = createNewUserSuccessPrecondition(user);
        StatusResponse expectedResponse = new StatusResponse(200, "unknown", "27282828292929929");
        int expectedCode = 200;
        String expectedType = "unknown";
        SoftAssertions softAssertions = new SoftAssertions();

        //Вариант оформления 1 для проверки через softAssertions
        softAssertions
                .assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("message")
                .isEqualTo(expectedResponse);
        softAssertions.assertThat(response.message()).isNotNull();
        softAssertions.assertAll();

        // Вариант оформления 2 для проверки через SoftAssertions
        checkNewUserStatus(response.message(), response.code(), expectedCode, response.type(), expectedType);
    }

    // ЗАДАЧА 3
    @Test
    @Feature("Создать лист из пользователей")
    @Description("POST/user/createWithList Creates list of users with given input array https://petstore.swagger.io/v2/pet")
    public void createNewListOfUsers() {
        // Когда сформирована коллекция пользователей
        NewUser[] usersArray = new NewUser[10];
        ;
        for (int i = 0; i < 10; i++) {
            String userName = "Генрих Великий" + i;
            System.out.println(userName);
            NewUser user = NewUser.createNewUser()
                    .id(i)
                    .username(userName)
                    .userStatus(1)
                    .build();
            usersArray[i] = user;
        }

        // Тогда она может быть успешно создана через POST/user/createWithList
        StatusResponse response = createNewUserCollectionSuccess(usersArray);
        checkNewUserStatus(response.message(), response.code(), 200, response.type(), "unknown");

        // И тогда все пользователи присутствуют в базе пользователей
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            String user = "Генрих Великий" + i;
            System.out.println(user);
           NewUser result = checkUserIsExistByNameSuccess(user);
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getUserStatus()).isEqualTo(1);
            });
        }
    }
}
