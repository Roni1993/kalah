Feature: Playarea
  As a Player i need a Playarea that provides all the necessary fields
  that are needed to play Kalah. For Kalah the playarea needs fields for
  two players. Each player has 6 fields that are called houses that
  contain 4 seeds. Additionally to the houses there is the seedstore
  that contains all seeds that the Player has won.

  Scenario: An normal play has started
    Given a new Kalah game has started
    And each Player should have 6 houses
    And each Player should have a seedstore
    And the playarea must contain 48 seeds

  Scenario: An normal play has started
    Given a new Kalah game has started
    Then the playarea must exactly be:
      | O | H | H | H | H | H | H | T |
      | 0 | 4 | 4 | 4 | 4 | 4 | 4 |   |
      |   | 4 | 4 | 4 | 4 | 4 | 4 | 0 |

  Scenario: An specific play was setup
    Given a Kalah game was set up with
      | O | H | H | H | H | H | H | T |
      | 6 | 3 | 3 | 3 | 3 | 3 | 3 |   |
      |   | 4 | 4 | 4 | 3 | 3 | 3 | 3 |
    Then the playarea must contain 48 seeds
    And the playarea must exactly be:
      | O | H | H | H | H | H | H | T |
      | 6 | 3 | 3 | 3 | 3 | 3 | 3 |   |
      |   | 4 | 4 | 4 | 3 | 3 | 3 | 3 |
