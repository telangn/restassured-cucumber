package APITesting.RestAssuredCucumber;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;

import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.*;
import junit.framework.Assert;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import SupportClasses.Info;
import SupportClasses.Posts;

public class StepDefinitions {

	/**
	 * given() 
	 * set cookies, add authorization, add parameters, setting header info
	 * when() 
	 * GET, POST, PUT, DELETE..etc 
	 * then() 
	 * validate status code, extract, response, extract headers, cookies, body
	 */

	public Response response;
	public RequestSpecification request;
	public RequestSpecBuilder requestBuilder;
	public ResponseSpecBuilder responseBuilder;
	public ValidatableResponse json;
	public String URL;
	public List<String> info = null;
	public int sizeOfData;
	public String member;
	public int value;

	/*
	 * @Before 
	 * public static void init() {
	 * 
	 * RestAssured.baseURI = "http://localhost"; 
	 * RestAssured.port = 3000;
	 * RestAssured.basePath = "/posts";
	 * 
	 * requestBuilder = new RequestSpecBuilder();
	 * Add parameters, headers, etc. with "requestBuilder"
	 * request = requestBuilder.build();
	 * pass "request" in as - given().spec(request).when().get();
	 * 
	 * responseBuilder = new ResponseSpecBuilder();
	 * Assert (expect) values with responseBuilder
	 * response = responseBuilder.build();
	 * pass "response" in as - given().spec().when().get().then().spec(response);
	 * 
	 * }
	 */

	// ====================== GIVEN ==================================

	@Given("^the endpoint \"([^\"]*)\" is up$")
	public void endPointUp(String arg1) throws Throwable {

		URL = arg1;
		response = given().when().get(URL);
		Assert.assertEquals(200, response.getStatusCode());
	}

	// ====================== WHEN ===================================

	@When("^I make a request to \"([^\"]*)\"$")
	public void requestURL(String arg1) throws Throwable {
		URL = URL + arg1;
	}

	@When("^I want to (?:get|patch|put|delete) data on id \"([^\"]*)\"$")
	public void updateOnEndPoint(String arg1) throws Throwable {
		URL = URL + arg1;
	}

	@When("^I pass in the parameters$")
	public void parameters(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		request = given()
				.param(data.get(0).get(0), data.get(0).get(1))
				.param(data.get(1).get(0), data.get(1).get(1));
	}

	@When("^I want to make a post with data$")
	public void postWithData(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		Info info = new Info();
		info.setPublisher(data.get(3).get(1));
		info.setIsbn(data.get(4).get(1));
		info.setCatalogNumber(data.get(5).get(1));

		Posts newPost = new Posts();
		newPost.setId(data.get(0).get(1));
		newPost.setTitle(data.get(1).get(1));
		newPost.setAuthor(data.get(2).get(1));
		newPost.setInfo(new Info[] { info });

		request = given().when().body(newPost).contentType(ContentType.JSON);
	}

	@When("^I want to (?:patch|put) with data$")
	public void putWithData(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		Posts newPost = new Posts();
		newPost.setTitle(data.get(0).get(1));
		newPost.setAuthor(data.get(1).get(1));

		request = given().when().body(newPost).contentType(ContentType.JSON);

	}

	@When("^I save data at key \"([^\"]*)\"$")
	public void saveDataAtPath(String arg1) throws Throwable {

		info = given().when().get(URL).then().extract().path(arg1);

	}

	@When("^I want to get data on member \"([^\"]*)\"$")
	public void Member(String arg1) throws Throwable {
		member = arg1;
	}


	@When("^there exists arrays for name \"([^\"]*)\" with value \"([^\"]*)\"$")
	public void searchKeysForValue(String arg1, String arg2) throws Throwable {
		info = given().when().get(URL).then().extract().path(member + ".findAll{it." + arg1 + "=='" + arg2 + "'}");
		assertThat(info.isEmpty(), is(false));

		/* Using Hamcrest: 
		 * assertThat((Collection)info, is(not(empty())));*/
	}

	// ========================= THEN ========================================

	@Then("^the weather description is \"([^\"]*)\"$")
	public void weatherDescription(String arg1) throws Throwable {

		response = request.get(URL);
		String weatherDescription = response.then().contentType(ContentType.JSON).extract()
				.path("weather[0].description");
		Assert.assertEquals(arg1, weatherDescription);

	}

	@Then("^the status code is \"([^\"]*)\"$")
	public void statusCode(int arg1) throws Throwable {

		Assert.assertEquals(arg1, response.getStatusCode());
	}

	@Then("^the body contains the key \"([^\"]*)\" with value \"([^\"]*)\"$")
	public void bodyContainsKeyAndValue(String arg1, String arg2) throws Throwable {

		
		/*  Using Hamcrest: 
		 * request.expect().body(arg1, equalTo(arg2)).when().get(URL);*/		 

		response = request.get(URL);
		String actualValue = response.then().contentType(ContentType.JSON).extract().path(arg1);
		Assert.assertEquals(arg2, actualValue);

	}

	@Then("^the data arrays are greater than (\\d+)$")
	public void dataArraysGreaterThan(int arg1) throws Throwable {

		/* Using Rest-assured: 
		 * sizeOfData = given().when().get(URL).then().extract().path(arg1 +".size()");*/		 

		assertThat(info.size(), greaterThan(arg1));
	}


	@Then("^there exists key \"([^\"]*)\" with value \"([^\"]*)\"$")
	public void valueFromSearch(String arg1, String arg2) throws Throwable {
		
		info = given().when().get(URL).then().extract()
				.path(member + ".findAll{it." + arg1 + "=='" + arg2 + "'}." + arg1);

		ArrayList<String> expectedValue = new ArrayList<String>();
		expectedValue.add(arg2);
		assertThat(info, CoreMatchers.hasItems(arg2));
	}

	
	@Then("^there exists an array of \"([^\"]*)\" whose \"([^\"]*)\" is greater than \"([^\"]*)\"$")
	public void arrayWithKeyGreaterThan(String arg1, String arg2, String arg3) throws Throwable {
		
		info = given().when().get(URL).then().extract()
				.path(member + ".findAll{it." + arg2 + ">'" + arg3 + "'}." + arg1);		
		assertThat(info.isEmpty(), is(false));
	}
	

	@Then("^I make the post$")
	public void post() throws Throwable {

		response = request.post(URL);
	}

	@Then("^I make the put$")
	public void put() throws Throwable {

		response = request.put(URL);
	}

	@Then("^I make the patch$")
	public void patch() throws Throwable {

		response = request.patch(URL);
	}

	@Then("^I make the delete$")
	public void delete() throws Throwable {

		response = given().when().delete(URL);
	}

}
