package stepDefinitions;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import junit.framework.Assert;
import static org.junit.Assert.*;
import java.util.List;
import org.hamcrest.CoreMatchers;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import SupportClasses.Catalog;
import SupportClasses.Book;

@SuppressWarnings("deprecation")
public class StepDefinitions {

	/* Notes:
	 * given() 
	 * set cookies, add authorization, add parameters, setting header info
	 * when() 
	 * GET, POST, PUT, DELETE..etc 
	 * then() 
	 * validate status code, extract, response, extract headers, cookies, body
	 */

	public Response response; //not used yet
	public ResponseSpecification responseSpec; //not used yet
	public ResponseSpecBuilder responseBuilder; //not used yet
	public ValidatableResponse VResponse;
	public RequestSpecification requestSpec;
	public RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
	
	public List<String> value = null;
	public String member;
	

	/* Need to Re-factor with this in mind:
	 * 
	 * RestAssured.rootPath = "";
	 * set it instead of using - given().when().then().root()
	 * 
	 * responseBuilder = new ResponseSpecBuilder();
	 * Assert (expect) values with responseBuilder
	 * responseSpec = responseBuilder.build();
	 * pass "responseSpec" in as - given().spec().when().get().then().spec(responseSpec).body();
	 */

	@Given("^the endpoint is up$")
	public void endPointUp() throws Throwable {
		VResponse = RestAssured.given().when().get().then();
		VResponse.statusCode(200);
	}

	@When("^I want to (?:get|patch|put|delete) data on id \"([^\"]*)\"$")
	public void updateOnEndPoint(String arg1) throws Throwable {
		requestBuilder.setBasePath(arg1);
	}

	@When("^I pass in the parameters$")
	public void parameters(DataTable arg1) throws Throwable {
		
		java.util.List<java.util.List<String>> data = arg1.raw();

		requestBuilder
		.addParam(data.get(0).get(0), data.get(0).get(1))
		.addParam(data.get(1).get(0),data.get(1).get(1));
	}

	@When("^I want to make a post with data$")
	public void postWithData(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		Catalog catalog = new Catalog();
		catalog.setPublisher(data.get(3).get(1));
		catalog.setIsbn(data.get(4).get(1));
		catalog.setCatalogNumber(data.get(5).get(1));

		Book book = new Book();
		book.setId(data.get(0).get(1));
		book.setTitle(data.get(1).get(1));
		book.setAuthor(data.get(2).get(1));
		book.setCatalog(new Catalog[] { catalog });

		requestBuilder.setBody(book).setContentType(ContentType.JSON);
	}

	@When("^I want to (?:patch|put) with data$")
	public void putWithData(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		Book book = new Book();
		book.setTitle(data.get(0).get(1));
		book.setAuthor(data.get(1).get(1));

		requestBuilder.setBody(book).setContentType(ContentType.JSON);
	}

	@When("^I save data at key \"([^\"]*)\"$")
	public void saveDataAtPath(String arg1) throws Throwable {
		value = VResponse.extract().path(arg1);
	}

	@When("^I want to get data on member \"([^\"]*)\"$")
	public void Member(String arg1) throws Throwable {
		member = arg1;
	}

	@When("^there exists arrays for name \"([^\"]*)\" with value \"([^\"]*)\"$")
	public void searchKeysForValue(String arg1, String arg2) throws Throwable {
		
		value = VResponse.extract().path(member + ".findAll{it." + arg1 + "=='" + arg2 + "'}");
		assertThat(value.isEmpty(), is(false));
	}

	@Then("^the status code is \"([^\"]*)\"$")
	public void statusCode(int arg1) throws Throwable {
		VResponse.statusCode(arg1);
	}

	@Then("^the body contains the key \"([^\"]*)\" with value \"([^\"]*)\"$")
	public void bodyContainsKeyAndValue(String arg1, String arg2) throws Throwable {

		String value = VResponse.extract().path(arg1);
		Assert.assertEquals(arg2, value);
	}

	@Then("^the data arrays are greater than (\\d+)$")
	public void dataArraysGreaterThan(int arg1) throws Throwable {

		int sizeOfData = VResponse.extract().path(member + ".size()");
		assertThat(sizeOfData, greaterThan(arg1));
	}

	@Then("^there exists key \"([^\"]*)\" with value \"([^\"]*)\"$")
	public void valueFromSearch(String arg1, String arg2) throws Throwable {
		
		value = VResponse.extract().path(member + ".findAll{it." + arg1 + "=='" + arg2 + "'}." + arg1);
		assertThat(value, CoreMatchers.hasItems(arg2));
	}

	@Then("^there exists an array of \"([^\"]*)\" whose \"([^\"]*)\" is greater than \"([^\"]*)\"$")
	public void arrayWithKeyGreaterThan(String arg1, String arg2, String arg3) throws Throwable {

		value = VResponse.extract().path(member + ".findAll{it." + arg2 + ">'" + arg3 + "'}." + arg1);
		assertThat(value.isEmpty(), is(false));
	}

	@Then("^I make the get$")
	public void get() throws Throwable {
		requestSpec = requestBuilder.build();
		VResponse = RestAssured.given().spec(requestSpec).when().get().then();
	}
	
	@Then("^I make the post$")
	public void post() throws Throwable {
		requestSpec = requestBuilder.build();
		VResponse = RestAssured.given().spec(requestSpec).when().post().then();
	}

	@Then("^I make the put$")
	public void put() throws Throwable {
		requestSpec = requestBuilder.build();
		VResponse = RestAssured.given().spec(requestSpec).when().put().then();
	}

	@Then("^I make the patch$")
	public void patch() throws Throwable {
		requestSpec = requestBuilder.build();
		VResponse = RestAssured.given().spec(requestSpec).when().patch().then();
	}

	@Then("^I make the delete$")
	public void delete() throws Throwable {
		requestSpec = requestBuilder.build();
		VResponse = RestAssured.given().spec(requestSpec).when().delete().then();
	}
}
