@weatherMap 
Feature: Testing REST APIs on openweathermap.org 

@setupWeather 
Scenario: GET Weather Response by City 
	Given the endpoint is up 
	When I want to get data on id "/data/2.5/weather" 
	And I pass in the parameters 
	
		| q | London | 
		| appid | a4dcb7fbc11efc9b1cefe7badc619f54 |
		
	Then I make the get 
	And the status code is "200" 
	
Scenario Outline: GET JSON Body Key and Values 
	Given the endpoint is up 
	When I want to get data on id "/data/2.5/weather" 
	And I pass in the parameters 
	
		| q | London | 
		| appid | a4dcb7fbc11efc9b1cefe7badc619f54 |
		
	Then I make the get 
	And the body contains the key "<key>" with value "<value>" 
	Examples: 
		| key | value |
		| name | London |
		|weather[0].description| light rain |
