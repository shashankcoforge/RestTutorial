package training.put;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import training.pojo.Employee;

public class BDDPutRestTest {
	private RequestSpecification requestSpecification;
	private Response response;
	private String baseUrl = "https://petstore.swagger.io/v2";
	String petId;

	@BeforeClass
	public void beforeClass() {
		RestAssured.baseURI = baseUrl;
	}

	@Test
	public void verifycreate() {
		String jsonString = "{\n" + "  \"id\": 0,\n" + "  \"category\": {\n" + "    \"id\": 1,\n"
				+ "    \"name\": \"Husky\"\n" + "  },\n" + "  \"name\": \"doggie\",\n" + "  \"photoUrls\": [\n"
				+ "    \"string\"\n" + "  ],\n" + "  \"tags\": [\n" + "    {\n" + "      \"id\": 123,\n"
				+ "      \"name\": \"Shitzu\"\n" + "    }\n" + "  ],\n" + "  \"status\": \"available\"\n" + "}";

		response = RestAssured.given().contentType(ContentType.JSON).body(jsonString).when().post("/pet").then()
				.statusCode(200).log().body().extract().response();
		petId = response.body().jsonPath().get("id").toString();
		System.out.println(petId);
	}

	@Test(dependsOnMethods = "verifycreate")
	public void verifyUpdate() {
		String jsonString = "{\n" + "  \"id\": "+ petId + ",\n" + "  \"category\": {\n" + "    \"id\": 1,\n"
				+ "    \"name\": \"Husky\"\n" + "  },\n" + "  \"name\": \"doggie\",\n" + "  \"photoUrls\": [\n"
				+ "    \"string\"\n" + "  ],\n" + "  \"tags\": [\n" + "    {\n" + "      \"id\": 123,\n"
				+ "      \"name\": \"Shitzu\"\n" + "    }\n" + "  ],\n" + "  \"status\": \"sold\"\n" + "}";

		response = RestAssured.given().contentType(ContentType.JSON).body(jsonString).when().put("/pet").then()
				.statusCode(200).log().body().extract().response();

	}

	@Test(dependsOnMethods = "verifyUpdate")
	public void verifyUpdated() {

		RestAssured.given().contentType(ContentType.JSON).when().get("/pet/" + petId).then().statusCode(200)
				.body("status", Matchers.equalTo("sold")).log().body();
	}
	
	@Test(dependsOnMethods = "verifyUpdated")
	public void verifyDelete() {

		RestAssured.given().contentType(ContentType.JSON).when().delete("/pet/" + petId).then().statusCode(200)
				.log().body();
	}


}
