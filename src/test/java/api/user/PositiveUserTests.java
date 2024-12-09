package api.user;

import base.NewUser;
import base.StatusResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

public class PositiveUserTests extends BaseApiUserTest {

    @Test
    @Feature("Создать пользователя")
    @Description("Create user https://petstore.swagger.io/v2/pet")
    public void createNewUser() {

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
        StatusResponse response = createNewUserPrecondition(user);
        int expectedCode = 200;
        String expectedType = "unknown";
        checkNewUserStatus(response.message(), response.code(), expectedCode, response.type(), expectedType);
    }
}
