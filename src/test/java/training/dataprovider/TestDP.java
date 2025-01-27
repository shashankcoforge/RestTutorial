package training.dataprovider;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TestDP {
	
  @Test(dataProvider = "dp1")
  public void testJson(JSONObject obj) {
	  RestAssured.baseURI = "https://dummyjson.com";
	  RestAssured.given()
	  .contentType(ContentType.JSON)
	  .body(obj.toString())
	  .when()
	  .post("users/add")
	  .then()
	  .statusCode(201)
	  .log().body(true)
	  .extract().response();
  }

  @DataProvider
  public Iterator<JSONObject> dp1() {
	  ArrayList<JSONObject> objs = new ArrayList<JSONObject>();
	  objs.add(new JSONObject().put("firstName", "Rana").put("lastName", "Pratap").put("age", 31));
	  objs.add(new JSONObject().put("firstName", "Sagar").put("lastName", "Agarwal").put("age", 30));
	  return objs.iterator();
  }
  
  @Test(dataProvider = "dp2")
  public void testBasic(String fName, String lName, Integer age) {
	  JSONObject obj = new JSONObject();
	  obj.put("firstName", fName);
	  obj.put("lastName", lName);
	  obj.put("age", age);
	  
	  RestAssured.baseURI = "https://dummyjson.com";
	  RestAssured.given()
	  .contentType(ContentType.JSON)
	  .body(obj.toString())
	  .when()
	  .post("users/add")
	  .then()
	  .statusCode(201)
	  .log().body(true)
	  .extract().response();
  }

  @DataProvider
  public Object[][] dp2() {
    return new Object[][] {
    	new Object[] { "Shiva", "Kumar", 30 },
    	new Object[] { "Nishi", "Sharma", 25 },
    };
  }
  
  @Test(dataProvider = "dp3")
  public void testFile(String fileName) throws Exception {
	  String userDir = System.getProperty("user.dir");
	  JSONObject obj = new JSONObject(new String(Files.readAllBytes(Paths.get(userDir + "\\src\\test\\resources\\" + fileName))));
	  RestAssured.baseURI = "https://dummyjson.com";
	  RestAssured.given()
	  .contentType(ContentType.JSON)
	  .body(obj.toString())
	  .when()
	  .post("users/add")
	  .then()
	  .statusCode(201)
	  .log().body(true)
	  .extract().response();
  }
  
  @DataProvider
  public Object[][] dp3() {
    return new Object[][] {
      new Object[] { "sample1.json" },
      new Object[] { "sample2.json" },
    };
  }
  
  @Test(dataProvider = "dp4")
  public void testJsonFile(JSONObject obj) {
	  RestAssured.baseURI = "https://dummyjson.com";
	  RestAssured.given()
	  .contentType(ContentType.JSON)
	  .body(obj.toString())
	  .when()
	  .post("users/add")
	  .then()
	  .statusCode(201)
	  .log().body(true)
	  .extract().response();
  }

  @DataProvider
  public Iterator<JSONObject> dp4() throws Exception {
	  String userDir = System.getProperty("user.dir");
	  String content =  new String(Files.readAllBytes(Paths.get(userDir + "\\src\\test\\resources\\sample3.json")));
	  JSONArray objArr = new JSONArray(content);
	  ArrayList<JSONObject> objs = new ArrayList<JSONObject>();
		for (int i = 0; i < objArr.length(); i++) {
			objs.add(objArr.getJSONObject(i));
		}
	  return objs.iterator();
  }
  
}
