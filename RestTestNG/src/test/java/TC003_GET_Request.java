import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC003_GET_Request {

	
	@Test
	void validateGetMethod2() {
		//Specify base URI
		
		RestAssured.baseURI="https://reqres.in/";
		
		//Request Object
		RequestSpecification httprequest = RestAssured.given();
		
		//Response Object
		Response response = httprequest.request(Method.GET,"\r\n"
				+ "/api/users?page=2");
		
		//Print response in console window
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is : "+responseBody);
		
		// Verify Header part capture details of header
		
		String contentType = response.header("Content-Type");
		System.out.println("Content Type is : "+contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
		
		String contentEncoding = response.header("Content-Encoding");
		System.out.println("Content Encoding is : "+contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");
		
		long responsetime = response.getTime();
		System.out.println("Response Time : "+responsetime);
		ValidatableResponse validateResponse = response.then().log().all();
		validateResponse.statusCode(200);
		validateResponse.time(Matchers.lessThan(5000L));
	}
}
