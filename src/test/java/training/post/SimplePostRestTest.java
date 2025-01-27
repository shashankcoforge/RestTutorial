package training.post;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class SimplePostRestTest {
	private RequestSpecification requestSpecification;
	private Response response;
	private String baseUrl = "https://reqres.in";

	@BeforeClass
	public void beforeClass() {
		RestAssured.baseURI = baseUrl;

		// Create a request specification
		requestSpecification = RestAssured.given();
	}

	@Test
	public void testStatusCode() {
		String jsonString = "{\"name\":\"rishabh\",\"job\":\"tester\"}";
		
		requestSpecification.body(jsonString);

		// Calling POST method
		response = requestSpecification.post("/api/users");

		// Let's print response body.
		String resString = response.prettyPrint();
		System.out.println("Response Details : " + resString);

		// Get status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		ResponseBody body = response.body();
		Assert.assertNotNull(body.jsonPath().get("id"));
	}

	@Test
	public void testUserData() {

		// Calling POST method
		response = requestSpecification.get("/api/users/3");

		// Let's print response body.
		String resString = response.prettyPrint();
		System.out.println("Response Details : " + resString);

		// Get status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		ResponseBody body = response.body();
		Assert.assertNotNull(body.jsonPath().get("id"));
	}

}
