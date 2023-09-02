import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class ChangeUserInformationTest {

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
    public void changePositiveUserEmail() {
        ResponseUserModel user = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .when()
                .get("/api/auth/user")
                .as(ResponseUserModel.class);
        UserRegistrationModel changedUser = user.getUser();
        changedUser.setEmail("changedUserEmail@mail.ru");

        Response responseChanged = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .body(changedUser)
                .when()
                .patch("/api/auth/user");
        responseChanged.then().assertThat().statusCode(200);

        ResponseUserModel newUser = responseChanged.body().as(ResponseUserModel.class);
        Assert.assertThat(changedUser.getEmail(), equalToIgnoringCase(newUser.getUser().getEmail()));
    }
    @Test
    public void changePositiveUserName() {
        ResponseUserModel user = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .when()
                .get("/api/auth/user")
                .as(ResponseUserModel.class);
        UserRegistrationModel changedUser = user.getUser();
        changedUser.setName("khdkoza");

        Response responseChanged = given()
                .header("Content-type", "application/json")
                .header("Authorization", currentToken)
                .and()
                .body(changedUser)
                .when()
                .patch("/api/auth/user");
        responseChanged.then().assertThat().statusCode(200);

        ResponseUserModel newUser = responseChanged.body().as(ResponseUserModel.class);
        Assert.assertThat(changedUser.getName(), equalToIgnoringCase(newUser.getUser().getName()));
    }

    @Test
    public void changeNegativeUserWithoutAuth() {
        File json = new File("src/main/resources/changeNegativeUserWithoutAuthFile.json");
        Response responseChanged = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .patch("/api/auth/user");
        responseChanged.then().assertThat().statusCode(401);

        ChangeNegativeUserWithoutAuthResponseModel changeNegativeUserWithoutAuthResponseModel =
                responseChanged.body()
                        .as(ChangeNegativeUserWithoutAuthResponseModel.class);
        Assert.assertEquals("You should be authorised", changeNegativeUserWithoutAuthResponseModel.getMessage());
        Assert.assertFalse(changeNegativeUserWithoutAuthResponseModel.isSuccess());
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
