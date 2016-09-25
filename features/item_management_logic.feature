Feature: Fridge business logic

  Background:
    Given I am logged in to Fridgezone
    And I have listed the following items in my fridge:
      | name   | stock_level |
      | Pop    | Low         |
      | Cheese | Stocked     |
    And I am viewing the fridgezone page


  Scenario: Favorite items marked as "Out" stock level should be tagged as "shopping"
    Given I have already created the following tag associations:
      | item   | name     |
      | Cheese | favorite |
    And I am viewing the fridgezone page
    When I have updated the item "Cheese" with the following values:
      | Name   | Stock Level |
      | Cheese | Out         |
    Then the item "Cheese" should be marked as a shopping item