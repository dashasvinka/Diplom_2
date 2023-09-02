import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateUserWithOutAnyFieldsTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void createNewUniqueUserWithOutLogin(){
        File json = new File("src/main/resources/createNewUniqueUserWithOutLoginFile.json");
        Response responseDouble = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/auth/register");
        responseDouble.then().assertThat().statusCode(403);
        CreateNewUniqueUserWithOutLogin createNewUniqueUserWithOutLogin =
                responseDouble.body()
                        .as(CreateNewUniqueUserWithOutLogin.class);
        Assert.assertEquals("Email, password and name are required fields", createNewUniqueUserWithOutLogin.getMessage());
        Assert.assertFalse(createNewUniqueUserWithOutLogin.isSuccess());
    }

    @Test
    public void createNewUniqueUserWithOutName(){
        File json = new File("src/main/resources/createNewUniqueUserWithOutNameFile.json");
        Response responseDouble = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/auth/register");
        responseDouble.then().assertThat().statusCode(403);
        CreateNewUniqueUserWithOutName createNewUniqueUserWithOutName =
                responseDouble.body()
                        .as(CreateNewUniqueUserWithOutName.class);
        Assert.assertEquals("Email, password and name are required fields", createNewUniqueUserWithOutName.getMessage());
        Assert.assertFalse(createNewUniqueUserWithOutName.isSuccess());
    }

    @Test
    public void createNewUniqueUserWithOutPassword(){
        File json = new File("src/main/resources/createNewUniqueUserWithOutPasswordFile.json");
        Response responseDouble = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/auth/register");
        responseDouble.then().assertThat().statusCode(403);
        CreateNewUniqueUserWithOutPassword createNewUniqueUserWithOutPassword =
                responseDouble.body()
                        .as(CreateNewUniqueUserWithOutPassword.class);
        Assert.assertEquals("Email, password and name are required fields", createNewUniqueUserWithOutPassword.getMessage());
        Assert.assertFalse(createNewUniqueUserWithOutPassword.isSuccess());
    }
}
