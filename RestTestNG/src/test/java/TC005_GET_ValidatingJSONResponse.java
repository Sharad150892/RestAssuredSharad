import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC005_GET_ValidatingJSONResponse {
	
	
	
	@Test
	public void getVerifyJSON () {
		
		RestAssured.baseURI = "https://reqres.in/";
		
		RequestSpecification httpRequest = RestAssured.given().log().all();
		
		Response response = httpRequest.request(Method.GET,"/api/users?page=2");
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is : "+responseBody);
		
		Assert.assertEquals(responseBody.contains("Tobias"),true);
		
		long responsetime = response.getTime();
		System.out.println("Response Time : "+responsetime);
		ValidatableResponse validateResponse = response.then().log().all();
		validateResponse.statusCode(200);
		validateResponse.time(Matchers.lessThan(5000L));
		
	}

}
