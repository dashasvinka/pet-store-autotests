import io.restassured.RestAssured;
import org.junit.Before;

public class BaseApiTest {
    // Api TEST HELPER
    // PRECOND
    // всегда используемые

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
}

