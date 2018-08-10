@jsonServer2 
Feature: Test JSON Server data 

@setupLocal 
Scenario: Check the length of info 
	Given the endpoint is up 
	When I want to get data on id "/Books/103" 
	Then I make the get 
	When I want to get data on member "info" 
	Then the data arrays are greater than 1 
	
Scenario: Search through all arrays for specific key and value 
	Given the endpoint is up 
	And I want to get data on id "/Books/103" 
	And I want to get data on member "info" 
	Then I make the get 
	And there exists arrays for name "publisher" with value "DC" 
	And there exists key "catalogNumber" with value "3" 
	And there exists an array of "publisher" whose "catalogNumber" is greater than "3" 
	
	
	