package interview;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class TestVagrant {

	@Test
	public void testGet() {

		// Specify base URI
		RestAssured.baseURI = "https://gist.githubusercontent.com/kumarpani/";

		// Request Object
		RequestSpecification httprequest = RestAssured.given().log().all();

		// Response Object
		Response response = httprequest.request(Method.GET,
				"1e759f27ae302be92ad51ec09955e765/raw/184cef7125e6ef5a774e60de31479bb9b2884cb5/TeamRCB.json");

		ResponseBody body = response.getBody();

		// Print response in console window
		String responseBody = body.asString();
		System.out.println("Json Body is as Follows : " + responseBody);

		// Status code validation
		int statuscode = response.getStatusCode();
		System.out.println("Status Code is : " + statuscode);

		JsonPath jsonpath = new JsonPath(response.asString());

		int s = jsonpath.getInt("player.size()");

		for (int i = 0; i < s; i++) {
			String country = jsonpath.getString("player[" + i + "].country");
	        String role = jsonpath.getString("player["+i+"].role");

			if (!(country.equals("India"))) {
				System.out.println("Country of Player : " + country);
				Assert.assertNotEquals(country, "India");
			}
			if ((role.equals("Wicket-keeper"))) {
				System.out.println("Role of Player : "+role);
				Assert.assertEquals(role, "Wicket-keeper");
			}	        
		}

		long responsetime = response.getTime();
		System.out.println("Response Time : "+responsetime);
		ValidatableResponse validateResponse = response.then();
		validateResponse.time(Matchers.lessThan(5000L));

	}

}
