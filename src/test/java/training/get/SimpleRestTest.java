package training.get;

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

public class SimpleRestTest {
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

		// Calling GET method
		response = requestSpecification.get("/api/users");

		// Let's print response body.
		String resString = response.prettyPrint();
		System.out.println("Response Details : " + resString);

		// Get status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
	}

	@Test
	public void testQueryParameter() {

		// Setting query parameters
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("page", "2");
		requestSpecification.queryParams(queryParams);

		// Calling GET method
		response = requestSpecification.get("/api/users");

		// Let's print response body.
		String resString = response.prettyPrint();
		System.out.println("Response Details : " + resString);

		// Get status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
	}

	@Test
	public void testPathParameter() {

		// Calling GET method
		response = requestSpecification.get("/api/users/2");

		// Let's print response body.
		String resString = response.prettyPrint();
		System.out.println("Response Details : " + resString);

		// Get status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test
	public void testUserData() {

		// Calling GET method
		response = requestSpecification.get("/api/users/3");

		// Let's print response body.
		String resString = response.prettyPrint();
		System.out.println("Response Details : " + resString);

		// Get status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		ResponseBody body = response.body();
		String val = body.jsonPath().get("data.first_name");
		Assert.assertEquals(val, "Emma");
	}

}
