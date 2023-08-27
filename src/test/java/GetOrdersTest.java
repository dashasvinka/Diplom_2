import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class GetOrdersTest {

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
    public void getOrdersWithAuth() {
        ResponseUserModel user = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .when()
                .get("/api/auth/user")
                .as(ResponseUserModel.class);
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .get("/api/orders/all");
        response.then().assertThat().statusCode(200);
        GetFullInformationOrderModel getFullInformationOrderModel =
                response.body()
                        .as(GetFullInformationOrderModel.class);
        Assert.assertTrue(getFullInformationOrderModel.isSuccess());
        GetFullInformationOrderModel listOrder = response.body().as(GetFullInformationOrderModel.class);
        Assert.assertTrue(listOrder.getOrders().size() > 0);
    }

    @Test
    public void getOrdersWithOutAuth() {
        Response response = given()
                .header("Content-type", "application/json")
                .get("/api/orders/all");
        response.then().assertThat().statusCode(200);
        GetFullInformationOrderModel getFullInformationOrderModel =
                response.body()
                        .as(GetFullInformationOrderModel.class);
        Assert.assertTrue(getFullInformationOrderModel.isSuccess());
        GetFullInformationOrderModel listOrder = response.body().as(GetFullInformationOrderModel.class);
        Assert.assertTrue(listOrder.getOrders().size() > 0);
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
