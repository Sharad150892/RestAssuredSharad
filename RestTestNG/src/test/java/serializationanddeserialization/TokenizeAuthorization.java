package serializationanddeserialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.Assert;
import org.testng.annotations.Test;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class TokenizeAuthorization {
	
	
	@Test
	public String generateToken() {
			
		RestAssured.baseURI="https://bookstore.toolsqa.com/";
		 String R=given().log().all().header("Content-Type","application/json").body("{\r\n"
		 		+ "  \"userName\": \"VELOCITY\",\r\n"
		 		+ "  \"password\": \"Velocity@12345678\"\r\n"
		 		+ "}")
		 .when().post("/Account/v1/GenerateToken").
		 then().log().all().assertThat().body("status", equalTo("Success")).extract().response().asString();
        System.out.println("------------------------------");
        System.out.println(R);
		JsonPath J=new JsonPath(R);
		String r=J.getString("token");
		System.out.println(r);
		Assert.assertNotNull(r);
		return r;

		
	}
	
	@Test 
	public void addBookwithToken() {
		
		RestAssured.baseURI="https://bookstore.toolsqa.com/";
		TokenizeAuthorization TA=new TokenizeAuthorization();
		System.out.println(TA.generateToken());
		String R=given().log().all().header("Content-Type","application/json").header("Authorization","Bearer "+TA.generateToken()).body("{\r\n"
				+ "  \"userId\": \"2848814e-3558-4151-bbe1-0f5f5b29b1da\",\r\n"
				+ "  \"collectionOfIsbns\": [\r\n"
				+ "    {\r\n"
				+ "      \"isbn\": \"9781449331818\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}")
		 .when().post("/BookStore/v1/Books").
		 then().log().all().assertThat().statusCode(201).body("books", notNullValue()).extract().response().asString();
        System.out.println("------------------------------");
		
		
		
	}
	

	
	

}
