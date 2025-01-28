package training.authentication;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BDDAuthRestTest {
	private RequestSpecification requestSpecification;
	private Response response;
	private String baseUrl = "https://restapi.demoqa.com";

	@BeforeClass
	public void beforeClass() {
		RestAssured.baseURI = baseUrl;
	}

	@Test
	public void verifyAuthentication() {
		RestAssured.given()
		.contentType(ContentType.JSON)
		.when()
		.get("/authentication/CheckForAuthentication")
		.then()
		.statusCode(200).log().body();
	}
	
	@Test
	public void dummyTest() {
		MatcherAssert.assertThat("1", Matchers.equalToIgnoringCase("1"));		
	}
	
}
