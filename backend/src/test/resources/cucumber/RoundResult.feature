Feature: Determine Game State
  As player i want that the game correctly determines the next
  game state. It either should choose the next player that plays
  or should check whether the game has ended.

  Scenario: Repeat round after Player has ended in the seedstore
    Given a new Kalah game has started
    When Player One is sowing from house no. 3
    Then the Round result should be ExtraRound

  Scenario: Player changes after Player has ended in a house
    Given a new Kalah game has started
    When Player One is sowing from house no. 2
    Then the Round result should be NextPlayer

  Scenario: Retry after Player sows from an empty house
    Given a Kalah game was set up with
      | O | H | H | H | H | H | H | T |
      | 4 | 4 | 4 | 4 | 4 | 4 | 0 |   |
      |   | 4 | 4 | 4 | 4 | 4 | 4 | 0 |
    When Player One is sowing from house no. 1
    Then the Round result should be Retry

  Scenario: Game has ended since one Player has no seeds left
    Given a Kalah game was set up with
      | O  | H | H | H | H | H | H | T |
      | 23 | 1 | 0 | 0 | 0 | 0 | 0 |   |
      |    | 4 | 4 | 4 | 4 | 4 | 4 | 0 |
    When Player One is sowing from house no. 6
    Then the Round result should be GameOver