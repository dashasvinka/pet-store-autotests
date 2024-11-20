package pet;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseApiTest {

    // В данном классе степы для каждого API Test //

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
}

