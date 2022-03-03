@db
Feature: As a QE, validating that I can connect to database

 Background: Connecting the database
   Given User is able to connect to database

  Scenario Outline: Validating the db connection
    And User sends the "<query>" to database
    Examples: Query for the database
      | query                             |
      | select min(salary) from employees |

  Scenario Outline: Validating the db connection
    And User sends the "<query>" to database to get min <salary>
    Examples: Query for the database

      | query                             |salary|
      | select min(salary) from employees |2100  |