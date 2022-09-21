import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC004_Get_Request_PrintAllHeader {
	
	@Test
	public void getRequestPrintHeader() {
		
		// Specify base URI
		
		RestAssured.baseURI ="https://reqres.in/";
		
		//Request object
		
		RequestSpecification httpRequest = RestAssured.given().log().all();
		
		//Response object
		Response response = httpRequest.request(Method.GET,"/api/users?page=2");
		
		//Print Response in console Window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is : "+responseBody);
		
		Headers allheaders = response.headers();	//capture all header from response
		
		for(Header header:allheaders) 
		{
			System.out.println(header.getName()+"   "+header.getValue());			
			
		}
		
		long responsetime = response.getTime();
		System.out.println("Response Time : "+responsetime);
		ValidatableResponse validateResponse = response.then().log().body();
		validateResponse.statusCode(200);
		validateResponse.time(Matchers.lessThan(5000L));
			
	}
	

}
