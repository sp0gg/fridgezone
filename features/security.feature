Feature: Security
  In order to ensure privacy and security
  As a user
  I want to see only my data

  Scenario: Users see only their own data
    Given I have listed the following items in my fridge:
      | name         | stock_level | username     |
      | Pop          | Low         |              |
      | Cheese       | Stocked     |              |
      | PRIVATE ITEM | Stocked     | PRIVATE_USER |
    And I am logged in to Fridgezone
    And I am viewing the fridgezone page
    Then I should see the following items on the page:
      | Name   | Stock Level |
      | Pop    | Low         |
      | Cheese | Stocked     |
    And I should NOT see the following items on the page:
      | Name         | Stock Level |
      | PRIVATE ITEM | Stocked     |