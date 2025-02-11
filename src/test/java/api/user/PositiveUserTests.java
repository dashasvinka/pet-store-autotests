package api.user;

import base.NewUser;
import base.StatusResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
    // DEFECT: POST/user/createWithList не кладет в базу всех пользователей
    @Test
    @Feature("Создать лист из пользователей")
    @Description("POST/user/createWithList Creates list of users with given input array https://petstore.swagger.io/v2/pet")
    public void createNewListOfUsers() {
        // Когда сформирована коллекция пользователей
        NewUser[] usersArray = new NewUser[10];

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

        // Тогда она может быть успешно создана через POST/user
        for (int i = 0; i < 10; i++) {
            System.out.println(usersArray[i]);
            StatusResponse response = createNewUserSuccessPrecondition(usersArray[i]);
            checkNewUserStatus(response.message(), response.code(), 200, response.type(), "unknown");
        }

        // И тогда все пользователи присутствуют в базе пользователей
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            String user = "Генрих Великий" + i;
            System.out.println(user);
            checkUserIsExistByName(user);
        }
    }

    // ЗАДАЧА 4
    // DEFECT: POST/user/createWithList не кладет в базу всех пользователей
    @Test
    @Feature("Удалить коллекцию из пользователей")
    @Description("DELETE/user/{username} Delete user https://petstore.swagger.io/v2/pet")
    public void deleteListOfUsers() {
        // Когда сформирована коллекция имен пользователей
        HashSet<String> h = new HashSet<String>();

        for (int i = 0; i <= 10; i++) {
            String userName = "Бубнов А" + i;
            h.add(userName);
        }

        Iterator<String> i = h.iterator();
        while (i.hasNext()) {
            String name = i.next();
            System.out.println(name);
            NewUser user = NewUser.createNewUser()
                    .id(1)
                    .username(name)
                    .userStatus(1)
                    .build();
            // Тогда она может быть успешно создана через POST/user
            StatusResponse response = createNewUserSuccessPrecondition(user);

            // И тогда пользователь присутствует в базе пользователей
            checkNewUserStatus(response.message(), response.code(), 200, response.type(), "unknown");
            checkUserIsExistByName(name);

            // И тогда пользователь может быть удален
            deleteUserIsExistByName(name);

            // Тогда они более не присутствуют
            checkUserIsNotExistByName(name);
        }
    }

    // ЗАДАЧА 5
    // DEFECT: POST/user/createWithList не кладет в базу всех пользователей
    @Test
    @Feature("Удалить коллекцию из пользователей c определенным статусом")
    @Description("DELETE/user/{username} Delete user https://petstore.swagger.io/v2/pet")
    public void deleteListOfUsersWithStatus() {

        // Когда сформирована коллекция имен пользователей
        HashMap<String, Integer> nameAndStatus = new HashMap<>();
        for (int i = 0; i <= 10; i++) {
            String userName = "Буcя" + i;
            int status;
            if (i < 5) {
                status = 0;
            } else {
                status = 1;
            }
            nameAndStatus.put(userName, status);
        }

        for (int i = 0; i < 10; i++) {
            String userName = "Буcя" + i;
            int status = nameAndStatus.get(userName);
            System.out.println(userName);
            NewUser user = NewUser.createNewUser()
                    .id(i)
                    .username(userName)
                    .userStatus(status)
                    .build();

            // Тогда она может быть успешно создана через POST/user
            StatusResponse response = createNewUserSuccessPrecondition(user);

            // И тогда пользователь присутствует в базе пользователей
            checkNewUserStatus(response.message(), response.code(), 200, response.type(), "unknown");
            checkUserIsExistByName(userName);
        }

        for (int i = 0; i < 10; i++) {
            String userName = "Буcя" + i;
            int status = nameAndStatus.get(userName);
            if (status == 1) {
                // И тогда пользователь может быть удален со статусом 1
                deleteUserIsExistByName(userName);

                // Тогда они более не присутствуют
                checkUserIsNotExistByName(userName);
            }
            if (status == 0){
                // И тогда пользователь со статусом 0 остается в базе
                checkUserIsExistByName(userName);
            }
        }

    }
}
