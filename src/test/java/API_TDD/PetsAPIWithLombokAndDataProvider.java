package API_TDD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo_classes.pets.Category;
import pojo_classes.pets.CreatePet;
import pojo_classes.pets.Tags;
import utils.DataProviderUtils;

import java.util.Collections;

import static utils.ApiUtils.serializePOJO;


public class PetsAPIWithLombokAndDataProvider {

    Faker faker = new Faker();
    Response response;
    ObjectMapper objectMapper = new ObjectMapper();

    private static Logger logger = LogManager.getLogger(PetsAPIWithLombokAndDataProvider.class);

    int actualPetId;
    String actualPetName;
    String actualPetStatus;
    int actualCategoryId;
    String actualCategoryName;
    int actualTagsId;
    String actualTagsName;
    String actualPhotoUrl;

    @BeforeSuite
    public void testingStarts() {
      logger.info("Starting API Automation Test with the TestNG");
    }

    @BeforeTest
    public void beforeTesting() {
        RestAssured.baseURI = utils.ConfigReader.getProperty("PetsURL");
    }

    @Test(dataProvider = "DataFromExcel", dataProviderClass = DataProviderUtils.class)
    public void addPet(String petId, String categoryId, String categoryName, String petName, String photoURL, String tagId, String tagName, String status) throws JsonProcessingException {

        Category buildCategory = Category.builder().id(Integer.parseInt(categoryId)).name(categoryName).build();

        Tags buildTags = Tags.builder().id(Integer.parseInt(tagId)).name(tagName).build();

        CreatePet createPet = CreatePet.builder().id(Integer.parseInt(petId)).name(petName).status(status)
                .category(buildCategory)
                .photoUrls(Collections.singletonList(photoURL))
                .tags(Collections.singletonList(buildTags))
                .build();

        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(serializePOJO(createPet))
                .post("/v2/pet")
                .then().log().all().assertThat().statusCode(200)
                .extract().response();

/**
 * Getting the actuall attributes' values from response body
 */
//        actualPetId = JsonPath.read(response.asString(), "id");
////        actualPetId = 5;
//        System.out.println("Actual PetId from the response body: " + actualPetId);
//
//        // The hamcrest assertion can be used for all the attributes' validation
//
//        assertThat(
//                "I am expecting Pet Id is: "+ createPet.getId(),
//                actualPetId,
//                is(createPet.getId())
//        );
//
//        logger.info("I am validating the petId");
//        logger.debug("The expected petId should be " + createPet.getId() + " we found " + actualPetId);
//        logger.error("The expected petId should be " + createPet.getId() + " we found " + actualPetId);
//
//        Assert.assertEquals(actualPetId, createPet.getId());
//
//        actualPetName = JsonPath.read(response.asString(), "name");
//        Assert.assertEquals(actualPetName, createPet.getName());
//
//        actualPetStatus = JsonPath.read(response.asString(), "status");
//        Assert.assertEquals(actualPetStatus, createPet.getStatus());
//
//        actualCategoryId = JsonPath.read(response.asString(), "category.id");
//        Assert.assertEquals(actualCategoryId, createPet.getCategory().getId());
//
//        actualCategoryName = JsonPath.read(response.asString(), "category.name");
//        Assert.assertEquals(actualCategoryName, createPet.getCategory().getName());
//
//        actualTagsId = JsonPath.read(response.asString(), "tags[0].id");
//        Assert.assertEquals(actualTagsId, createPet.getTags().get(0).getId());
//
//        actualTagsName = JsonPath.read(response.asString(), "tags[0].name");
//        Assert.assertEquals(actualTagsName, createPet.getTags().get(0).getName());
//
//        actualPhotoUrl = JsonPath.read(response.asString(), "photoUrls[0]");
//        Assert.assertEquals(actualPhotoUrl, createPet.getPhotoUrls().get(0));
    }
}
