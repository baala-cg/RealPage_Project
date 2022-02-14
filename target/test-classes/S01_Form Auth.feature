Feature: Form Authentication        
    
    @FormAuthentication
    @Test123
  	Scenario: Validate Authentication of User
  	Given User navigates to Url
  	And Scenario Name as "Form Authentication"
    When User logs into the application and validate home page
    And Validate user logs out of the application
    
  