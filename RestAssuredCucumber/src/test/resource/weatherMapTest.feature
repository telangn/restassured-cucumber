@runsecondfile 
Feature: Testing REST APIs on openweathermap.org 

Scenario: GET Weather Response by City 
	Given the endpoint "http://api.openweathermap.org" is up 
	When I make a request to "/data/2.5/weather" 
	And I pass in the parameters 
	
		| q | London | 
		| appid | a4dcb7fbc11efc9b1cefe7badc619f54 |
		
	Then the weather description is "mist" 
	And the status code is "200" 
	
Scenario Outline: GET JSON Body Key and Values 
	Given the endpoint "http://api.openweathermap.org" is up 
	When I make a request to "/data/2.5/weather" 
	And I pass in the parameters 
	
		| q | London | 
		| appid | a4dcb7fbc11efc9b1cefe7badc619f54 |
		
	Then the body contains the key "<key>" with value "<value>" 
	Examples: 
		| key | value |
		| name | London |
		|weather[0].description| mist |
