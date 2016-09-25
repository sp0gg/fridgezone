Feature: Fridge inventory manager

  Background:
    Given I am logged in to Fridgezone
    And I have listed the following items in my fridge:
      | name   | stock_level |
      | Pop    | Low         |
      | Cheese | Stocked     |
    And I am viewing the fridgezone page

  Scenario: View fridge products
    Then I should see the following items on the page:
      | Name   | Stock Level |
      | Pop    | Low         |
      | Cheese | Stocked     |

  Scenario: Add fridge product
    When I have added the following items:
      | Name      | Stock Level |
      | Pizza     | Low         |
      | Ice cream | Stocked     |
    Then I should see the following items on the page:
      | Name      | Stock Level |
      | Pizza     | Low         |
      | Ice cream | Stocked     |
      | Pop       | Low         |
      | Cheese    | Stocked     |

  Scenario: Update items
    When I have updated the item "Cheese" with the following values:
      | Name      | Stock Level |
      | Provolone | Out         |
    Then I should see the following items on the page:
      | Name      | Stock Level |
      | Pop       | Low         |
      | Provolone | Out         |
    And I should NOT see the following items on the page:
      | Name   | Stock Level |
      | Cheese | Stocked     |