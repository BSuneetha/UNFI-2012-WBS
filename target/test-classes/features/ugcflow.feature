Feature: Access Burst.com website,Perform ugc customizations and try to upload a media to bubble 

Scenario: Login into the burst website and perform ugc customization for anonymous flow with media submission type video only 
	Given I open burst website 
    When I sign in 
    And I click on the recent bubble that was created
    And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
    And I select UGC tab from the Bubble Edit page to configure the UGC settings
    And I open the ugc link in New Tab upload a video file and after finishing validate it redirects to correct url
    And I click on CANCEL button to close the UGC link popup
    Then I sign out
    
Scenario: Login into the burst website and perform ugc customization for anonymous flow with media submission type video only and custom image
	Given I open burst website 
    When I sign in 
    And I click on the recent bubble that was created
    And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
    And I select UGC tab from the Bubble Edit page to configure the UGC settings
    And I open the ugc link in New Tab upload a video file and after finishing validate it redirects to correct url
    And I click on CANCEL button to close the UGC link popup
    Then I sign out
	
Scenario: Login into the burst website and perform ugc customization for anonymous flow with media submission type video only and custom image
	Given I open burst website 
    When I sign in 
	And click on account menu option
	And I click on Edit button of Company logo for ugc screens
	
	
Scenario: Login into the burst website and perform ugc customization for anonymous flow with media submission type video only with 
         custom image in logo & headline,making media title a required field,with dedicated screen with accept buttons in T & C.
	Given I open burst website 
    When I sign in 
    And I click on the recent bubble that was created
    And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
    And I select UGC tab from the Bubble Edit page to configure the UGC settings
    And I open the ugc link in New Tab upload a video file and after finishing validate it redirects to correct url
    And I click on CANCEL button to close the UGC link popup
    Then I sign out
	