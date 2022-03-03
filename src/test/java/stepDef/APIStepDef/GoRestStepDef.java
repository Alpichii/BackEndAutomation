package stepDef.APIStepDef;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo_classes.users.CreateUserWithLombok;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static utils.ApiUtils.serializePOJO;
import static utils.ConfigReader.getProperty;

public class GoRestStepDef {

    Response response;
    int userId;

    @Given("Create user with {string}, {string}, email, and {string}")
    public void createUserWithEmailAnd(String name, String gender, String status) {

        CreateUserWithLombok createUserWithLombok = CreateUserWithLombok
                .builder().name(name).gender(gender).status(status).build();

        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", getProperty("Token"))
                .body(serializePOJO(createUserWithLombok))
                .post(getProperty("GoRestURL") + "/public/v2/users")
                .then().log().all().extract().response();

        userId= JsonPath.read(response.asString(), "id");

    }

    @And("Validate that status code is {int}")
    public void validateThatStatusCodeIs(int expectedStatusCode) {

        int actualStatusCode=response.getStatusCode();

        assertThat(
                "I am expecting status code: "+ expectedStatusCode,
                actualStatusCode,
                is(expectedStatusCode)
        );
    }

    @And("Make GET call to get user with {string}")
    public void makeGETCallToGetUserWith(String url) {

        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", getProperty("Token"))
                .get(url+userId)
                .then().log().all().extract().response();
    }
}
