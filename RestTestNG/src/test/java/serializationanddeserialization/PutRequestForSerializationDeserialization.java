package serializationanddeserialization;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PutRequestForSerializationDeserialization {
	
	
	
	
	@Test(priority=1)
	public void putrequest() {
		
		PojoRequestBody requestbody = new PojoRequestBody();
		requestbody.setName("morpheus");
		requestbody.setJob("zion president");
		
		RestAssured.baseURI = "https://reqres.in/";
		
//		RequestSpecification httpRequest = RestAssured.given().log().all();
		
		PojoResponseBody response = given().log().all().header("Content-Type","application/json")
		.body(requestbody).expect().defaultParser(Parser.JSON).when().put("/api/users/2").then().log().all()
		.assertThat().statusCode(200).body("job", equalTo("zion president")).extract().response().as(PojoResponseBody.class);
		
		System.out.println("<------------------------------------------------------------------------------------------------>");
		System.out.println(response.getJob());
	}
	
	
	@Test(priority=2)
	public void putRequestByAnotherMethod() {
			
		RestAssured.baseURI = "https://reqres.in";

		RequestSpecification httpRequest = RestAssured.given().log().all();
		
		JSONObject requestParam = new JSONObject();
		
		requestParam.put("name", "Raju");
		requestParam.put("job", "Joker");
		
		httpRequest.header("Content-Type","application/json");
		
		httpRequest.body(requestParam.toJSONString());
		
		Response response = httpRequest.request(Method.PUT,"/api/users/2");
		
		String responseBody = response.getBody().asString();
		
		System.out.println("Response Body is : "+responseBody);
		
	}
	
	

}





















