package training.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import training.pojo.Employee;
import training.pojo.Project;

public class PojoTest {
	private RequestSpecification requestSpecification;
	private Response response;
	private String baseUrl = "https://reqres.in";

	@BeforeClass
	public void beforeClass() {
		RestAssured.baseURI = baseUrl;
	}

	@Test
    public void gsonSerializationTest()  {
 
        // Create an object of POJO class
        Employee employee = new Employee();
        employee.setFirstName("Vibha");
        employee.setLastName("Singh");
        employee.setAge(30);
        employee.setSalary(75000);
        employee.setDesignation("Manager");
        employee.setContactNumber("+919999988822");
//        employee.setEmailId("abc@test.com");
        
        Project pr = new Project();
        employee.setProjects(Arrays.asList(pr));
 
        Gson gson = new Gson();
        String employeeJsonPayload = gson.toJson(employee);
        System.out.println("Json :" + employeeJsonPayload);
 
        
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        String employeePrettyJsonPayload = builder.toJson(employee);
        System.out.println("Pretty Json :" + employeePrettyJsonPayload);
 
    }
	
	@Test
	 public void getDetailFromJson() {
	      
	     // De-serializing from JSON String
	     String jsonString = "{\r\n" + "  \"firstName\": \"Tom\",\r\n" + "  \"lastName\": \"John\",\r\n"
	             + "  \"age\": 30,\r\n" + "  \"salary\": 50000.0,\r\n" + "  \"designation\": \"Lead\",\r\n"
	             + "  \"contactNumber\": \"+917642218922\",\r\n" + "  \"emailId\": \"abc@test.com\"\r\n" + "}";
	 
	     Gson gson = new Gson();
	     // Pass JSON string and the POJO class
	     Employee employee = gson.fromJson(jsonString, Employee.class);
	 
	     // Now use getter method to retrieve values
	     System.out.println("Details of Employee is as below:-");
	     System.out.println("First Name : " + employee.getFirstName());
	     System.out.println("Last Name : " + employee.getLastName());
	     System.out.println("Age : " + employee.getAge());
	     System.out.println("Salary : " + employee.getSalary());
	     System.out.println("designation : " + employee.getDesignation());
	     System.out.println("contactNumber : " + employee.getContactNumber());
	     System.out.println("emailId : " + employee.getEmailId());
	     System.out.println("########################################################");
	 
	 }
	
	@Test
    public void fromFile() throws FileNotFoundException {
 
        Gson gson = new Gson();
        // De-serializing from a json file
        String userDir = System.getProperty("user.dir");
        File inputJsonFile = new File(userDir + "\\src\\test\\resources\\EmployeePayloadUsingGson.json");
        FileReader fileReader = new FileReader(inputJsonFile);
        Employee employee1 = gson.fromJson(fileReader, Employee.class);
 
        // Now use getter method to retrieve values
        System.out.println("Details of Employee is as below:-");
        System.out.println("First Name : " + employee1.getFirstName());
        System.out.println("Last Name : " + employee1.getLastName());
        System.out.println("Age : " + employee1.getAge());
        System.out.println("Salary : " + employee1.getSalary());
        System.out.println("designation : " + employee1.getDesignation());
        System.out.println("contactNumber : " + employee1.getContactNumber());
        System.out.println("emailId : " + employee1.getEmailId());
        System.out.println("########################################################");
    }
	
}
