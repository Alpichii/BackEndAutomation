Feature: As a QE, I validate the GoRest API CRUD operation

  @api
  Scenario Outline: Validating the GoRest API CRUD operation
    Given Create user with "<name>", "<gender>", email, and "<status>"
    And Validate that status code is 201
    And Make GET call to get user with "<URL>"
    And Validate that status code is 200


    Examples: GoRest data
      | name        | gender | status |URL|
      | Tech Global | female | active | https://gorest.co.in/public/v2/users/  |