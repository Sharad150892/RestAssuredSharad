import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC006_Get_ExtractValueofEachNodeJSON {
	
	
	@Test
	public void getValueOfNodeJson(){
		RestAssured.baseURI="https://reqres.in";
		
		RequestSpecification httpRequest = RestAssured.given().log().all();
		
		Response response = httpRequest.request(Method.GET,"/api/users?delay=3");
		
		JsonPath jsonpath = response.jsonPath();
		
		System.out.println("Page : "+jsonpath.get("page"));
		System.out.println("Per_Page : "+jsonpath.get("per_page"));
		System.out.println("total : "+jsonpath.get("total"));
		System.out.println("total_pages : "+jsonpath.get("total_pages"));
		System.out.println("data : "+jsonpath.get("data"));
		
		Assert.assertEquals(jsonpath.get("page"), 1);
		Assert.assertEquals(jsonpath.get("total"), 12);
		
		long responsetime = response.getTime();
		System.out.println("Response Time : "+responsetime);
		ValidatableResponse validateResponse = response.then().log().all();
		validateResponse.statusCode(200);
		validateResponse.time(Matchers.lessThan(5000L));
	}

}
