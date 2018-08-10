package stepDefinitions;

import com.jayway.restassured.RestAssured;
import cucumber.api.java.Before;

public class Hooks {

	// pass in as - RestAssured.given();

	@Before("@setupLocal")
	public static void initLocal() {

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
		RestAssured.basePath = "/Books";
	}

	@Before("@setupWeather")
	public static void initWeather() {

		RestAssured.baseURI = "http://api.openweathermap.org";
	}
}
