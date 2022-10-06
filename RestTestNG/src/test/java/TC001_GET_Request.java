import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC001_GET_Request {
	
	@Test
	void validateGetMethod() {
		//Specify base URI
		
		RestAssured.baseURI="https://reqres.in/";
		
		//Request Object
		RequestSpecification httprequest = RestAssured.given().log().all();
		
		//Response Object
		Response response = httprequest.request(Method.GET,"/api/users?page=2");
		
		//Print response in console window
		
		String responseBody = response.getBody().asString();
		
		System.out.println("Response Body is : "+responseBody);
		
		//Status code validation
		int statuscode = response.getStatusCode();
		System.out.println("Status Code is : "+statuscode);	//Optional
		Assert.assertEquals(statuscode, 200);
		
		//Status Line verification
		String statusLine = response.getStatusLine();
		System.out.println("Status Line is : "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
		
		
		long responsetime = response.getTime();
		System.out.println("Response Time : "+responsetime);
		ValidatableResponse validateResponse = response.then().log().all();
		System.out.println("Validate Response :-"+validateResponse);
		validateResponse.statusCode(200);
		validateResponse.time(Matchers.lessThan(5000L));
	}

}




















