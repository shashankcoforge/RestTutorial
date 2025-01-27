package training.get;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class BDDRestTest {
	private RequestSpecification requestSpecification;
	private Response response;
	private String baseUrl = "https://reqres.in";

	@BeforeClass
	public void beforeClass() {
		RestAssured.baseURI = baseUrl;
	}

	@Test
	public void verifyStatusCode() {
		RestAssured.given()
		.when()
		.get("/api/users")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void verifyQueryParameter() {
		RestAssured.given()
		.queryParam("page", 2)
		.when()
		.get("/api/users")
		.then()
		.statusCode(200)
		.body("page", Matchers.equalTo(2));
	}
	
	@Test
	public void verifyPathParameter() {
		RestAssured.given()
		.when()
		.get("/api/users/3")
		.then().assertThat()
		.statusCode(200)
		.body("data.first_name", Matchers.equalTo("Emma"));
	}
}
