Feature: Access Burst.com website,Perform Login logout 

Scenario: Login into the burst website with remember me checkbox enabled 
	Given I open burst Login Page 
	And  I Toggle the RememberMe check box to on 
	When I sign in directly from login page 
	And I go to profile page 
	And Search for email address in profile page to be testautomation_dev@burst.us 
	And I open the burst website in New Tab 
	Then It should go to profile page directly 
	Then I sign out 
	
Scenario: Sign in and sign out from the burst website 
	Given I open burst website 
	When I sign in 
	And I go to profile page 
	And Search for email address in profile page to be testautomation_dev@burst.us 
	Then I sign out 
	
Scenario: Signing up a new basic account from the burst website 
	Given I open burst Login Page 
	When I click on sign up 
	And I choose Basic 
	And I fill in the form details for basic 
	And click on signup button 
	And I click on ok button on Thanks for signing up 
	And I go to profile page 
	And Search for email address in profile page to be automation_xxxx@burst.us 
	Then I sign out 
	
Scenario: Signing up a new enterprise account from the burst website 
	Given I open burst Login Page 
	When I click on sign up 
	And I choose enterprise 
	And I fill in the form details for enterprise 
	And click on signup button 
	And I click on ok button on Thanks for signing up 
	And I go to profile page 
	And Search for email address in profile page to be automation_xxxx@burst.us 
	Then I sign out 
	
	
Scenario Outline:
Sign in and sign out from the burst website with multiple values 
	Given I open burst website 
	And click sign in button to open sign in page 
	When I fill in "<email>" "<password>" 
	And click login button 
	And I go to profile page 
	And Check for email address in profile page to be "<email>" 
	Then I sign out 
	
	Examples: 
		|     email                   |password      | 
		| sambadev_biztest@burst.us   | innominds    |  
		| seenudonthula@gmail.com      | innominds     |       
		
Scenario Outline:
Sign in and sign out from the burst website with multiple values 
	Given I open burst website 
	And click sign in button to open sign in page 
	When I fill in "<email>" "<password>" 
	And click login button 
	And click on account menu option 
	And Check for email address in account page to be "<email>" and feature bundle to be "<featureid>" 
	Then I sign out 
	
	Examples: 
		|     email                   |password      | featureid |
		| sambadev_biztest@burst.us   | innominds    |  Business - Custom Enterprise |
		| seenudonthula@gmail.com      | innominds     | Enterprise - Custom 131 - BETA |
		| selenium320@gmail.com        | innominds     | Business - Advanced  |
		| testconsumerbasic@burst.us | innominds  | Consumer - Basic |
		#   
		#Scenario: Sigining in to the application using valid credentials and check user can able to access all the available modules
		#	Given I open burst website
		#    When I sign in 
		#    And I click on each and every tab on screen
		#    Then I sign out
		
		
		Scenario Outline: Signin and signout from burst applicaiton using facebook credentials
			Given I open burst website
			And click sign in button to open sign in page
			And click on login with facebook button
			And enter facebook credentials "<email> " "<password>" and click on login on FB login page
			And I go to profile page 
			And Check for email address in profile page to be "<email>"
		    Then I sign out
			
		Examples:
		    |     email                   |password      | 
		    | selenium320@gmail.com   | innominds    |  
		
		