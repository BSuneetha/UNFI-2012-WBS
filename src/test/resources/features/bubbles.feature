Feature: Access Burst.com website,create bubbles and perform actions

Scenario: Sign in to the burst website and create a basic bubble with approved status set to pending

Given I visit burst website 
When I sign in 
And I click on create new "BASIC" bubble button
And I select YES from Do you want to continue to UGC and Widget configuration to create a bubble
And I select WIDGET tab from the Bubble Edit page to configure the widget settings
Then I toggle Require Approval for Bubble Member Submissions to create a bubble 


Scenario: Sign in to the burst website and import multiple media to an existing bubble

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page 
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I click on ADD Media button to add media in to the bubble 
And Click on START Import button to import media 
Then I click on close button in file has been successfully uploaded into your account screen

Scenario: Sign in to the burst website and select media that is imported to change the media status to Approved

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page 
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media uploaded date to open the media pop up screen
Then I Click on Approve Button to Approve the media and Close the dialog


Scenario Outline:: Sign in to the burst website and join bubble

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page 
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I click on PLUS icon on bubble detail page to open Buble Pin dialog and copy the bubble pin
Then I sign out
When I open burst website
And click sign in button to open sign in page 
And  I fill in "<email>" "<password>"
And click login button 
And I click on My Bubbles tab to open Bubble List page 
Then I click on JOIN BUBBLE button to join the bubble

Examples:
    |     email                   |password      | 
    | seenudonthula@gmail.com      | innominds     |  


Scenario: Sign in to the burst website and select media to add a new title and tags to it

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page 
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media uploaded date to open the media pop up screen
And I click on EDIT link in the Media Information section to add media title and tags information
Then I Click on SAVE Button to save the media and Close the dialog

Scenario: Sign in to the burst website and select multiple media to add a new title and tags to each media

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page 
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media uploaded date and click on edit link to add media info and save the media 


Scenario Outline: Sign in to the burst website and Approve media from Moderate Media tab

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I click on MODERATE Media button to perform actions on media
And I click on checkbox against media item
And I click on "<BUTTON>" with text "<BUTTON>" Select media
Then I click on "<BUTTON>" and check the title
Then I sign out

Examples: 
		|    BUTTON                  |  
		|    REJECT                  | 
		|    APPROVE                 |
		
		


#Scenario: Sign in to the burst website and create a Keyword bubble with approved status set to pending
#
#Given I open burst website 
#When I sign in 
#And I click on create new "KEYWORD" bubble button
#And I select YES from Do you want to continue to UGC and Widget configuration to create a bubble
#And I select WIDGET tab from the Bubble Edit page to configure the widget settings
#Then I toggle Require Approval for Bubble Member Submissions to create a bubble 

Scenario Outline:: Sign in to the burst website and share a single media to facebook

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
#And I Hover the mouse on the media and click on media checkbox to open the media ribbon page
And I Hover the mouse on the media and click on media checkbox of a "<MEDIATYPE>" to open the media ribbon page

And I click on share icon on the media ribbon page to open share this media popup
And I fill in the details and click on "facebook" checkbox in social media section
And I switch to the facebook login popup and sign in to facebook
And I click on Share button 
Then I open facebook page verify the "oldwaytitle" and sign off
Then I sign out

Examples: 
        | MEDIATYPE | 
		| PHOTO     |  
		| VIDEO     | 


Scenario: Sign in to the burst website and share media(photo) to facebook using native share post to timeline

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media checkbox of a "PHOTO" to open the media ribbon page
#And I Hover the mouse on the media and click on media checkbox to open the media ribbon page
And I click on share icon on the media ribbon page to open share this media popup
And I click on Native Share tab and click on Login with Facebook button 
And I switch to the facebook login popup and sign in to facebook
And I click on Post to timeline radio button
And I fill in the "PHOTO" details for "post to timeline" and click on Share Button
And I open facebook page click on photos tab and verify the "mediatitle" and signoff
Then I sign out

Scenario: Sign in to the burst website and share media(video) to facebook using native share post to timeline

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media checkbox of a "VIDEO" to open the media ribbon page
#And I Hover the mouse on the media and click on media checkbox to open the media ribbon page
And I click on share icon on the media ribbon page to open share this media popup
And I click on Native Share tab and click on Login with Facebook button 
And I switch to the facebook login popup and sign in to facebook
And I click on Post to timeline radio button
And I fill in the "VIDEO" details for "post to timeline" and click on Share Button
Then I open facebook page verify the "mediatitle" and sign off
Then I sign out


Scenario: Sign in to the burst website and share media(photo) to facebook using native share post to page

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media checkbox of a "PHOTO" to open the media ribbon page
#And I Hover the mouse on the media and click on media checkbox to open the media ribbon page
And I click on share icon on the media ribbon page to open share this media popup
And I click on Native Share tab and click on Login with Facebook button 
And I switch to the facebook login popup and sign in to facebook
And I fill in the "PHOTO" details for "post to a page" and click on Share Button
And I open facebook page click on pages tab verify the "mediatitle" and sign off
Then I sign out

Scenario: Sign in to the burst website and share media(video) to facebook using native share post to page

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media checkbox of a "VIDEO" to open the media ribbon page
#And I Hover the mouse on the media and click on media checkbox to open the media ribbon page
And I click on share icon on the media ribbon page to open share this media popup
And I click on Native Share tab and click on Login with Facebook button 
And I switch to the facebook login popup and sign in to facebook
And I fill in the "VIDEO" details for "post to a page" and click on Share Button
And I open facebook page click on pages tab verify the "nativewaytitle" and sign off
Then I sign out

Scenario: Sign in to the burst website and share media to twitter using native share

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media checkbox to open the media ribbon page
And I click on share icon on the media ribbon page to open share this media popup
And I click on Native Share tab and click on Login with Twitter button 
And I switch to the twitter login popup and sign in to twitter
And I enter the text to be tweeted and click on share button
And I open twitter page verify the "nativeshare" title and signoff
Then I sign out

Scenario: Sign in to the burst website and share media to twitter 

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media checkbox to open the media ribbon page
And I click on share icon on the media ribbon page to open share this media popup
And I fill in the details and click on "twitter" checkbox in social media section
And I switch to the twitter login popup and sign in to twitter
And I click on Share button
And I open twitter page verify the "oldway" title and signoff
Then I sign out

Scenario: Sign in to the burst website and share multiple photos (media) to facebook using native share post to timeline

Given I open burst website 
When I sign in 
And I click on the recent bubble that was created
#And I click on My Bubbles tab to open Bubble List page
And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
And I Hover the mouse on the media and click on media checkbox of all "PHOTOS" to open the media ribbon page
And I click on share icon on the media ribbon page to open share this media popup
And I click on Native Share tab and click on Login with Facebook button 
And I switch to the facebook login popup and sign in to facebook
And I click on Post to timeline radio button
And I fill in the "PHOTO" details for "post to timeline" and click on Share Button
And I open facebook page click on photos tab and verify the "mediatitle" and signoff
Then I sign out


Scenario: Sign in to the burst website and create a Keyword bubble with approved status set to pending

Given I open burst website 
When I sign in 
And I click on create new "KEYWORD" bubble button
And I select YES from Do you want to continue to UGC and Widget configuration to create a bubble
And I select WIDGET tab from the Bubble Edit page to configure the widget settings
Then I toggle Require Approval for Bubble Member Submissions to create a bubble 

#Scenario: Sign in to the burst website and create a basic bubble with approved status set to pending
#
#Given I visit burst website 
#When I sign in 
#And I click on My Bubbles tab to open Bubble List page
#And I select bubble with title TEST_BUBBLE_AUTOMATION on BML page
#And I select UGC tab from the Bubble Edit page to configure the UGC settings
##Then I toggle Require Approval for Bubble Member Submissions to create a bubble 
