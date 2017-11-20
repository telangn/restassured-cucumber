@runfile 
Feature: Testing Rest APIs 

Scenario: Get Weather Response by City 
	Given the endpoint "http://api.openweathermap.org" is up 
	When I make a request to "/data/2.5/weather" 
	And I pass in the parameters 
	
		| q | London | 
		| appid | a4dcb7fbc11efc9b1cefe7badc619f54 |
		
	Then the weather description is "light intensity drizzle rain" 
	And the status code is "200" 
	
Scenario Outline: Get JSON Body Key and Values 
	Given the endpoint "http://api.openweathermap.org" is up 
	When I make a request to "/data/2.5/weather" 
	And I pass in the parameters 
	
		| q | London | 
		| appid | a4dcb7fbc11efc9b1cefe7badc619f54 |
		
	Then the body contains the key "<key>" with value "<value>" 
	Examples: 
		| key | value |
		| name | London |
		|weather[0].description| light intensity drizzle rain |
		
		
Scenario: Post Data to JSON Server 

	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to make a post with data 
	
		| id | 102 |
		| title | Wealth of Nations |
		| author | Adam Smith | 
		
	Then I make the post 
	And the status code is "201" 
	
Scenario: Put Data to JSON Server 
	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to put data on id "/1" 
	And I want to put with data 
	
		| title | To Kill a Mockingbird | 
		| author | Harper Lee | 
		
	Then I make the put 
	And the status code is "200" 
	
Scenario: Patch Data to JSON Server 
	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to patch data on id "/1" 
	And I want to patch with data 
	
		| title | To Kill a Mockingbird newPatch | 
		| author | Harper Lee | 
		
	Then I make the patch 
	And the status code is "200" 
	
	
	
	
