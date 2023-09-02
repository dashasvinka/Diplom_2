import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateOrderTest {
    private String currentToken;
    public void createUser() {
        File json = new File("src/main/resources/createNewUniqueUserFile.json");
        ResponseRegistrationModel responseRegistrationModel  =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/register")
                        .body()
                        .as(ResponseRegistrationModel.class);
        currentToken = responseRegistrationModel.getAccessToken();
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        createUser();
    }

    @Test
    public void createOrderFullListPositiveWithAut() {
        File json = new File("src/main/resources/createOrderFullListPositiveFile.json");
             given()
            .header("Content-type", "application/json")
            .header("Authorization", currentToken)
            .and()
            .when()
            .get("/api/auth/user");
    Response response = given()
            .header("Content-type", "application/json")
            .header("Authorization", currentToken)
            .and()
            .body(json)
            .when()
            .post("/api/orders");
        response.then().assertThat().statusCode(200);
        CreateOrderPositiveResponseModel createOrderPositiveResponseModel =
                response.body()
                    .as(CreateOrderPositiveResponseModel.class);
        Assert.assertTrue(createOrderPositiveResponseModel.isSuccess());
    }

    @Test
    public void createOrderEmptyListWithAut() {
        File json = new File("src/main/resources/createOrderEmptyListWithAutFile.json");
                 given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .when()
                .get("/api/auth/user");
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .body(json)
                .when()
                .post("/api/orders");
        response.then().assertThat().statusCode(400);
        CreateOrderEmptyListWithAutResponseModel createOrderEmptyListWithAutResponseModel =
                response.body()
                        .as(CreateOrderEmptyListWithAutResponseModel.class);
        Assert.assertFalse(createOrderEmptyListWithAutResponseModel.isSuccess());
        Assert.assertEquals("Ingredient ids must be provided", createOrderEmptyListWithAutResponseModel.getMessage());
    }

    @Test
    public void createOrderInvalidIngredientWithAuth() {
        File json = new File("src/main/resources/createOrderInvalidIngredientWithAuthFile.json");
                 given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .when()
                .get("/api/auth/user");
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .body(json)
                .when()
                .post("/api/orders");
        response.then().assertThat().statusCode(500);
    }

    @Test
    public void createOrderFullListNegativeWithOutAuth() {
        File json = new File("src/main/resources/createOrderFullListPositiveFile.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/orders");
        response.then().assertThat().statusCode(401);
    }
        @After
        public void authUser() {
            given()
                    .header("Content-type", "application/json")
                    .header("Authorization", currentToken)
                    .and()
                    .when()
                    .delete("/api/auth/user")
                    .body().print();
        }
}
