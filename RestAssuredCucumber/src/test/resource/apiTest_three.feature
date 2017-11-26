@runthirdfile 
Feature: Test JSON Server data 

Scenario: Check the length of info 
	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to get data on id "/100" 
	When I save data at key "info" 
	Then the data arrays are greater than 1 
	
Scenario: Search through all arrays for specific key and value 
	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to get data on id "/100" 
	And I want to get data on member "info"
<<<<<<< HEAD
	And there exists arrays for name "publisher" with value "DC"
	Then there exists key "catalogNumber" with value "3"
	And there exists an array of "publisher" whose "catalogNumber" is greater than "3"
=======
	And I search all keys with name "publisher" for value "DC"
	Then the "catalogNumber" of my search is "3"
>>>>>>> 11d6144543e977dc8c186beaf7b4a49443402074
	