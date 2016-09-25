Feature: Sort and filter by tags

  Background:
    Given I am logged in to Fridgezone
    And I have listed the following items in my fridge:
      | name    | stock_level |
      | Pop     | Low         |
      | Cheese  | Stocked     |
      | Cookies | Out         |
      | Chips   | Surplus     |
    And I have already created the following tag associations:
      | item    | name     |
      | Pop     | favorite |
      | Pop     | shopping |
      | Cookies | favorite |
      | Chips   | shopping |
    And I am viewing the fridgezone page

  Scenario: Filter by favorites tag
    When I have filtered the view by favorites
    Then I should see the following items on the page:
      | Name    | Stock Level |
      | Pop     | Low         |
      | Cookies | Out         |

    And I should NOT see the following items on the page:
      | Name   | Stock Level |
      | Cheese | Stocked     |
      | Chips  | Surplus     |

  Scenario: Add favorite tag
    When I have marked the item "Chips" as a favorite item
    Then the item "Chips" should be marked as a favorite item

  Scenario: Filter by shopping tag
    And I have filtered the view by shopping
    Then I should see the following items on the page:
      | Name  | Stock Level |
      | Pop   | Low         |
      | Chips | Surplus     |

    And I should NOT see the following items on the page:
      | Name    | Stock Level |
      | Cheese  | Stocked     |
      | Cookies | Out         |

  Scenario: Add shopping tag
    When I have marked the item "Chips" as a shopping item
    Then the item "Chips" should be marked as a shopping item

  Scenario: View custom tags in item view
    Given I have already created the following tag associations:
      | item   | name   |
      | Cheese | dairy  |
      | Cheese | snacks |
    And I am viewing the fridgezone page
    When I view the item "Cheese"
    Then the custom tag "dairy" should be present
    And the custom tag "snacks" should be present

  Scenario: Add custom tag in item view
    And I mark the item "Chips" with new custom tag "snacks"
    Then the item "Chips" should be marked with tag "snacks"

  Scenario: Delete custom tag in item view
    Given I have already created the following tag associations:
      | item  | name   |
      | Chips | snacks |
    And I am viewing the fridgezone page
    When I remove the custom tag "snacks" from the item "Chips"
    Then the item "Chips" should NOT be marked with tag "snacks"

  Scenario: Filter by custom tags
    Given I have already created the following tag associations:
      | item   | name     |
      | Pop    | favorite |
      | Pop    | shopping |
      | Pop    | snacks   |
      | Cheese | favorite |
      | Chips  | shopping |
      | Chips  | snacks   |
    And I am viewing the fridgezone page
    When I filter by custom tag "snacks"
    Then I should see the following items on the page:
      | Name  | Stock Level |
      | Pop   | Low         |
      | Chips | Surplus     |
    And I should NOT see the following items on the page:
      | Name    | Stock Level |
      | Cheese  | Stocked     |
      | Cookies | Out         |