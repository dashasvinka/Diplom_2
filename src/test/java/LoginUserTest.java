import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class LoginUserTest {

    public void createUser(){
        File json = new File("src/main/resources/createNewUniqueUserFile.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/register");
        response.then().assertThat().statusCode(200);
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        createUser();
    }


    @Test
    public void loginCorrectUser(){
        File json = new File("src/main/resources/authFile.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/login");
        response.then().assertThat().statusCode(200);
        ResponseRegistrationModel responseRegistrationModel =
                response.body()
                        .as(ResponseRegistrationModel.class);
        Assert.assertEquals("khdakhdakhda@yandex.ru", responseRegistrationModel.getUser().getEmail());
    }

    @Test
    public void loginIncorrectUser(){
        File json = new File("src/main/resources/authFileIncorrect.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/login");
        response.then().assertThat().statusCode(401);
        ResponseRegistrationModelIncorrectUser responseRegistrationModelIncorrectUser =
                response.body()
                        .as(ResponseRegistrationModelIncorrectUser.class);
        Assert.assertEquals("email or password are incorrect", responseRegistrationModelIncorrectUser.getMessage());
        Assert.assertFalse(responseRegistrationModelIncorrectUser.isSuccess());
    }

    @After
    public void authUser(){
        File json = new File("src/main/resources/authFile.json");
        ResponseRegistrationModel responseRegistrationModel =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/login")
                        .body()
                        .as(ResponseRegistrationModel.class);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", responseRegistrationModel.getAccessToken())
                .and()
                .when()
                .delete("/api/auth/user")
                .body().print();

    }
}