package API_TDD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo_classes.users.CreateUserWithLombok;

import static org.hamcrest.Matchers.equalTo;


public class GoRestWithLombok {

    Faker faker = new Faker();

    Response response;

    ObjectMapper objectMapper = new ObjectMapper();

    String actualName;
    int actualUserId;
    String actualEmail;
    String actualStatus;
    String actualGender;

    @BeforeSuite
    public void testingStarts() {
        System.out.println("API Automation starts");
    }

    @BeforeTest
    public void beforeTesting() {
        RestAssured.baseURI = utils.ConfigReader.getProperty("GoRestURL");
    }

    @Test
    public void CRUD() throws JsonProcessingException {
// Following object is used when there is no @AllArgsConstructor and @NoArgsConstructor annotations in the POJO
//        CreateUserWithLombok createUserWithLombok = new CreateUserWithLombok();

// We use following object when all the variables have values in the POJO
//        CreateUserWithLombok createUserWithLombok = CreateUserWithLombok.builder().build();

// Using following syntax when you need to assign the value in the class
// where you perform the automation
        CreateUserWithLombok createUserWithLombok = CreateUserWithLombok
                .builder().name("Tech Global").gender("female").build();
        System.out.println("My request body: " + createUserWithLombok);

        // created new user with using POJO class
        response = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", utils.ConfigReader.getProperty("Token"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createUserWithLombok))
                .when().post("/public/v2/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .body("name", equalTo("Tech Global")).extract().response();

//        String postResponseBody = response.getBody().asString();
//        System.out.println("POST Response body is: " + postResponseBody);
//
//        actualUserId = response.jsonPath().getInt("id");
//        actualName = response.jsonPath().getString("name");
//        actualEmail = response.jsonPath().getString("email");
//        actualStatus = response.jsonPath().getString("status");
//        actualGender = response.jsonPath().getString("gender");
//
//        // update the user with using the POJO class
//
//        UpdateUser updateUser = new UpdateUser();
//        updateUser.setName(faker.name().fullName());
//        updateUser.setEmail(actualEmail);
//        updateUser.setStatus(actualStatus);
//
//        response = RestAssured.given().log().all()
//                .header("Content-Type", "application/json")
//                .header("Authorization", utils.ConfigReader.getProperty("Token"))
//                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateUser))
//                .when().put("/public/v2/users/" + actualUserId)
//                .then().log().all()
//                .assertThat().statusCode(200)
//                .extract().response();
    }
}
