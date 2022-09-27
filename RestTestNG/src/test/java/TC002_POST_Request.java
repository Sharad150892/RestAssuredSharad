import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC002_POST_Request {

	@Test
	void validatePostMethod() {
		// Specify base URI
		RestAssured.baseURI = "https://reqres.in/";

		// Request Object
		RequestSpecification httpRequest = RestAssured.given().log().all();

		// Request PayLoad sending along with post request
		JSONObject requestParam = new JSONObject();

		requestParam.put("FirstName", "JohnXYZ");
		requestParam.put("LastName", "XYZJohn");
		requestParam.put("UserName", "JohnXYZ");
		requestParam.put("Password", "JohnXYZxyz");
		requestParam.put("Email", "JohnXYZ@gmail.com");

		httpRequest.header("Content-Type", "application/json");

		httpRequest.body(requestParam.toJSONString());

		// Response Object
		Response response = httpRequest.request(Method.POST, "/api/users");

		// print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is : " + responseBody);

		// Status code validation
		int statuscode = response.getStatusCode();
		System.out.println("Status Code is : " + statuscode); // Optional
		Assert.assertEquals(statuscode, 201);

		String successCode = response.jsonPath().get("SuccessCode");
		System.out.println("Success Code : "+successCode);
		
		long responsetime = response.getTime();
		System.out.println("Response Time : "+responsetime);
		ValidatableResponse validateResponse = response.then().log().all();
		validateResponse.statusCode(201);
		validateResponse.time(Matchers.lessThan(5000L));
	}

}
