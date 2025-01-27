package training.authentication;

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
	public void verifyStatusCodeWithJsonObject() {
		JSONObject bodyObj = new JSONObject();
		bodyObj.put("name", "shiva");
		bodyObj.put("job", "tester");
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(bodyObj.toString())
		.when()
		.post("/api/users")
		.then()
		.statusCode(201)
		.body("id", Matchers.notNullValue());
	}
	
	@Test
	public void verifyStatusCodeWithMap() {
		Map<String, String> bodyMap = new HashMap<String, String>();
		bodyMap.put("name", "shiva");
		bodyMap.put("job", "tester");
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(bodyMap)
		.when()
		.post("/api/users")
		.then()
		.statusCode(201)
		.body("name", Matchers.equalTo("shiva")).log().body();
	}
	
	@Test
    public void verifyStatusCodeWithJsonArray() {
		RestAssured.baseURI = "https://dummy.restapiexample.com";

        // JSON Object for first employee
        JSONObject data1 = new JSONObject();

        data1.put("employee_name", "ObjectTest");
        data1.put("profile_image", "test1.png");
        data1.put("employee_age", "30");
        data1.put("employee_salary", "11111");

        // JSON Object for second employee
        JSONObject data2 = new JSONObject();

        data2.put("employee_name", "MapTest");
        data2.put("profile_image", "test2.png");
        data2.put("employee_age", "20");
        data2.put("employee_salary", "99999");

        // Creating JSON array to add both JSON objects
        JSONArray array = new JSONArray();
        array.put(data1);
        array.put(data2);
        
        System.out.println(array.toString());

        // Send the request
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(array.toString())
                .when()
                .post("/api/v1/create")
                .then()
                .assertThat().statusCode(200)
                .body("message", Matchers.equalTo("Successfully! Record has been added."))
                .log().all();
    }
	
	@Test
    public void createEmployee() throws IOException {
 
        // Just create an object of Pojo class
        Employee emp = new Employee();
        emp.setFirstName("GsonTest");
        emp.setSalary(50000);
        emp.setAge(25);
 
        // Converting a Java class object to a JSON payload as string using Gson
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        String employeePrettyJsonPayload = builder.toJson(emp);
        System.out.println("Request");
        System.out.println(employeePrettyJsonPayload);
        System.out.println("=========================================");
        System.out.println("Response");
 
        // GIVEN
        RestAssured.given()
              .baseUri("https://dummyjson.com")
              .contentType(ContentType.JSON).body(emp)
 
        // WHEN
        .when()
               .post("/users/add")
 
        // THEN
        .then()
              .assertThat().statusCode(201)
              .body("firstName", Matchers.equalTo("GsonTest"))
              .body("age", Matchers.equalTo(25))
              .log().body();
 
    }
	
	@Test
    public void createEmployeeVerifyGson() throws IOException {
 
        // Just create an object of Pojo class
        Employee emp = new Employee();
        emp.setFirstName("GsonTest");
        emp.setSalary(50000);
        emp.setAge(25);
 
        // Converting a Java class object to a JSON payload as string using Gson
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        String employeePrettyJsonPayload = builder.toJson(emp);
        System.out.println("Request");
        System.out.println(employeePrettyJsonPayload);
        System.out.println("=========================================");
        System.out.println("Response");
 
        // GIVEN
        response = RestAssured.given()
              .baseUri("https://dummyjson.com")
              .contentType(ContentType.JSON).body(emp)
 
        // WHEN
        .when()
               .post("/users/add")
 
        // THEN
        .then()
              .assertThat().statusCode(201).log().body().extract().response();
        
        Employee employee = new Gson().fromJson(response.asString(), Employee.class);
        Assert.assertEquals(employee.getFirstName(), "GsonTest");
 
    }
	
	@Test
	public void dummyTest() {
		MatcherAssert.assertThat("1", Matchers.equalToIgnoringCase("1"));		
	}
	
}
