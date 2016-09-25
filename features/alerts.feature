Feature: Alerts
  In order to get feedback on item operations
  As a user
  I want to see alerts when things happen in the application

  Scenario: Alert when item is added
    Given I am logged in to Fridgezone
    And I am viewing the fridgezone page
    When I have added the following items:
      | Name  | Stock Level |
      | Pizza | Low         |
    Then I should see an alert with text: "Pizza added"

  Scenario: Alert when item is updated
    Given I am logged in to Fridgezone
    And I have listed the following items in my fridge:
      | name  | stock_level |
      | Pizza | Out         |
    And I am viewing the fridgezone page
    When I have updated the item "Pizza" with the following values:
      | Name  | Stock Level |
      | Pizza | Stocked     |
    Then I should see an alert with text: "Pizza updated"