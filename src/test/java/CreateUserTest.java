import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateUserTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void createNewUniqueUser(){
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

    @Test
    public void createNotUniqueUser(){
        File json = new File("src/main/resources/createNewUniqueUserFile.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/register");
        response.then().assertThat().statusCode(200);

               Response responseDouble = given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/auth/register");
        responseDouble.then().assertThat().statusCode(403);
        CreateUserFailedResponseForNotUnique createUserFailedResponseForNotUnique =
                responseDouble.body()
                        .as(CreateUserFailedResponseForNotUnique.class);
        Assert.assertEquals("User already exists", createUserFailedResponseForNotUnique.getMessage());
        Assert.assertFalse(createUserFailedResponseForNotUnique.isSuccess());
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
