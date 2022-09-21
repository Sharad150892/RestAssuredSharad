package datadriventesting;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class DataDrivenTest_AddNewEmployees {
	
	@Test(dataProvider="empdataprovider")
	void postNewEployees(String ename, String esal, String eage){
		
		//specify base URI
		RestAssured.baseURI ="http://dummy.restapiexample.com/api/v1";
		
		//request object
		RequestSpecification httpRequest = RestAssured.given().log().all();
		
		//Here we created data which we can send along with the post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", ename);
		requestParams.put("salary", esal);
		requestParams.put("age", eage);
		
		// Add header stating the request body is a Json
		httpRequest.header("Content-Type","application/json");
		
		// Add the Json to the body of request
		httpRequest.body(requestParams.toJSONString());
		
		// post request
		Response response = httpRequest.request(Method.POST,"/create");
		
		// capture response body to perform validation
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is : "+responseBody);
		
		Assert.assertEquals(responseBody.contains(ename), true);
		Assert.assertEquals(responseBody.contains(esal), true);
		Assert.assertEquals(responseBody.contains(eage), true);
		
		int statuscode = response.getStatusCode();
		Assert.assertEquals(statuscode, 200);
		
		
		long rtime = response.getTime();
		System.out.println("Response Time : "+rtime);
		ValidatableResponse validateResponse = response.then().log().all();
		validateResponse.statusCode(200);
		validateResponse.time(Matchers.lessThan(5000L));
		
	}
	
	@DataProvider (name="empdataprovider")
	String [][] getEmpData() throws IOException{
		
		
		// Read Data from Excel
		String path = "C:\\Users\\shara\\eclipse-practice\\RestTestNG\\src\\test\\java\\datadriventesting\\empdata.xlsx";
		int rownumber = XLUtils.getRowCount(path,"Sheet1");
		int colcount = XLUtils.getCellCount(path,"Sheet1" , 1);
		
		String empdata[][] = new String[rownumber][colcount];
		
		for(int i =1; i<=rownumber; i++) {
			for(int j = 0; j<colcount; j++) {
				empdata[i-1][j] = XLUtils.getCellData(path, "Sheet1", i, j);
			}
		}
		
		
//		String empdata[][] = {{"abc123","30000","40"},{"xyz123","40000","30"},{"pqr123","50000","28"}};
		return (empdata);
	}
	
}
