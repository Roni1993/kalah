Feature: Determine Winner
  As i player i want to know who has won the game after it ended

  Scenario Outline: Determining Winner
    Given Player One has <first> in the seedstore
    And Player Two has <second> in the seedstore
    And a game was ongoing with two players
    When the game ends
    Then the determined winner should be <winner>

    Examples:
      | first | second | winner |
      | 25    | 23     | One    |
      | 23    | 25     | Two    |
      | 24    | 24     | None   |