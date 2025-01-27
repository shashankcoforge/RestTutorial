package training.get;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class HamcrestRestTest {
	private RequestSpecification requestSpecification;

	@Test
	public void stringAssertions() {
		String endpoint = "https://restful-booker.herokuapp.com/booking/1";
		RestAssured.given().contentType(ContentType.JSON).when().get(endpoint).then().body("firstname",
				Matchers.equalTo("Susan"));

		RestAssured.given().contentType(ContentType.JSON).when().get(endpoint).then().body("firstname",
				Matchers.equalToIgnoringCase("susan"));

		RestAssured.given().contentType(ContentType.JSON).when().get(endpoint).then().body("firstname",
				Matchers.containsString("Sus"));

		RestAssured.given().contentType(ContentType.JSON).when().get(endpoint).then().body("firstname",
				Matchers.startsWith("S"));

		RestAssured.given().contentType(ContentType.JSON).when().get(endpoint).then().body("firstname",
				Matchers.endsWith("n"));

		RestAssured.given().contentType(ContentType.JSON).when().get(endpoint).then().body("firstname",
				Matchers.equalToIgnoringWhiteSpace("   Susan "));

	}
	
	@Test
    public void testMultipleAssertions() {
		String endpoint = "https://restful-booker.herokuapp.com/booking/1";
        RestAssured.given().contentType(ContentType.JSON)
                .when().get(endpoint).then()
                .body("firstname", Matchers.equalTo("Jim"), // will fail
                        "lastname", Matchers.equalTo("Smith"), // will fail
                        "totalprice", Matchers.equalTo(314)); // will fail
    }
}
