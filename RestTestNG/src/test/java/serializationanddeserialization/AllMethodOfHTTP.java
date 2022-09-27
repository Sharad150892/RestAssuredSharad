package serializationanddeserialization;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AllMethodOfHTTP {

	@Test(priority=1)
	public void getRequest() {

		RestAssured.baseURI = "https://reqres.in/";
		String R = given().log().all().header("Content-Type", "application/json").when().get("/api/users?page=2").then()
				.log().all().assertThat().statusCode(200).body("page", equalTo(2)).extract().response().asString();
		System.out.println("------------------------------");
		System.out.println(R);
		JsonPath J = new JsonPath(R);
		String r = J.getString("page");
		System.out.println(r);
		Assert.assertEquals(r, "2");
	}

	@Test(priority=2)
	public void postRequest() {

		RestAssured.baseURI = "https://reqres.in/";
		String R = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}").when()
				.post("/api/users").then().log().all().assertThat().statusCode(201).body("name", equalTo("morpheus"))
				.extract().response().asString();
		System.out.println("------------------------------");
		System.out.println(R);
	}

	@Test(priority=3)
	public void putRequest() {

		RestAssured.baseURI = "https://reqres.in/";
		String R = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"zion president\"\r\n" + "}").when()
				.put("/api/users/2").then().log().all().assertThat().statusCode(200)
				.body("job", equalTo("zion resident")).extract().response().asString();
		System.out.println("------------------------------");
		System.out.println(R);

	}

	
	@Test(priority=4)
	public void deleteRequest() {

		RestAssured.baseURI = "https://reqres.in/";
		given().log().all().header("Content-Type", "application/json").when().delete("/api/users/2").then().log().all()
				.assertThat().statusCode(204);
		System.out.println("------------------------------");

	}

	@Test(priority=5)
	public void authRequest() {

		RestAssured.baseURI = "https://postman-echo.com/";
		given().log().all().header("Content-Type", "application/json").auth().preemptive().basic("postman", "password")
				.when().get("/basic-auth").then().log().all().assertThat().statusCode(200);

	}

}
