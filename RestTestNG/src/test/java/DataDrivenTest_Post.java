import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class DataDrivenTest_Post {
	
	
	@Test
	void addNewEployees(){
		
		//specify base URI
		RestAssured.baseURI ="http://dummy.restapiexample.com/api/v1";
		
		//request object
		RequestSpecification httpRequest = RestAssured.given().log().all();
		
		//Here we created data which we can send along with the post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Raju");
		requestParams.put("salary", "10000");
		requestParams.put("age", "30");
		
		// Add header stating the request body is a Json
		httpRequest.header("Content-Type","application/json");
		
		// Add the Json to the body of request
		httpRequest.body(requestParams.toJSONString());
		
		// post request
		Response response = httpRequest.request(Method.POST,"/create");
		
		// capture response body to perform validation
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is : "+responseBody);
		
		Assert.assertEquals(responseBody.contains("Raju"), true);
		Assert.assertEquals(responseBody.contains("10000"), true);
		Assert.assertEquals(responseBody.contains("30"), true);
		
		int statuscode = response.getStatusCode();
		Assert.assertEquals(statuscode, 200);
		
		
		long rtime = response.getTime();
		System.out.println("Response Time : "+rtime);
		ValidatableResponse validateResponse = response.then().log().all();
		validateResponse.statusCode(200);
		validateResponse.time(Matchers.lessThan(5000L));
		
	}
	
	

}
