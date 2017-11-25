@runfile 
Feature: Testing REST APIs on JSON Server
		
Scenario: POST Data to JSON Server 

	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to make a post with data 
	
		| id | 103 |
		| title | The Outsiders |
		| author | S.E. Hinton | 
		| publisher | Viking Press |
		| isbn | 404 |
		| catalog no. | 5 |
		
	Then I make the post 
	And the status code is "201" 
	
Scenario: PUT Data to JSON Server 
	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to put data on id "/102" 
	And I want to put with data 
	
		| title | To Kill a Mockingbird | 
		| author | Harper Lee | 
		
	Then I make the put 
	And the status code is "200" 
	
Scenario: PATCH Data to JSON Server 
	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to patch data on id "/1" 
	And I want to patch with data 
	
		| title | Manufacturing Consent | 
		| author | Noam Chomsky | 
		
	Then I make the patch 
	And the status code is "200" 
	
Scenario: DELETE Data on JSON Server 
	Given the endpoint "http://localhost:3000" is up 
	When I make a request to "/posts" 
	And I want to delete data on id "/100" 
	Then I make the delete 
	And the status code is "200"
	
	
	
	
