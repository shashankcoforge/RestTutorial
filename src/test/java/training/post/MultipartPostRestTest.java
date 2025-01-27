package training.post;

import static com.github.tomakehurst.wiremock.client.WireMock.aMultipart;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;

public class MultipartPostRestTest {
	private RequestSpecification requestSpecification;
	private Response response;
	private String baseUrl = "https://reqres.in";
	private WireMockServer wireMockServer;

	@BeforeClass
	public void beforeClass() {
		RestAssured.baseURI = baseUrl;

		// Create a request specification
		requestSpecification = RestAssured.given();
		wireMockServer = new WireMockServer(options().port(8080));
	    wireMockServer.start();
	    
	    MultipartValuePatternBuilder multipartValuePatternBuilder = aMultipart()
	    		  .withName("file")
	    		  .withHeader("Content-Disposition", containing("file.txt"))
	    		  .withBody(equalTo("File content"))
	    		  .withHeader("Content-Type", containing("text/plain"));

	    		stubFor(post(urlEqualTo("/upload"))
	    		  .withMultipartRequestBody(multipartValuePatternBuilder)
	    		  .willReturn(aResponse().withStatus(200)));
	    
	    wireMockServer.stubFor(get(urlEqualTo("/download"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"name\": \"Shashank\", \"role\": \"Mentor\" }")));
	    
	    System.out.println("server started");
	}
	

	@AfterClass
	void stopServer() {
	    wireMockServer.stop();
	}

	@Test
	public void testUploadFile() {
		RestAssured.baseURI = "http://localhost:8080";

		MultiPartSpecification multiPartSpecification = new MultiPartSpecBuilder("File content".getBytes())
				  .fileName("file.txt")
				  .controlName("file")
				  .mimeType("text/plain")
				  .build();
		
		RestAssured.given()
		.multiPart(multiPartSpecification)
		.when()
		.post("/upload")
		.then()
		.statusCode(200)
		.log().all();
	}

}
