Feature: Sowing
  As a Player i want to be able to move on the playarea that is provided by the game.
  On this playarea i can choose a house that belongs to me and sow the containing seeds.
  When I'm sowing i need to move counter-clockwise and drop a seed into each house that
  I pass until I'm out of seeds. When I'm passing a seedstore i'm only allowed to put a
  seed into my own seedstore.

  Rule: Regular sowing has to work for both players
    Scenario: Player One is sowing
      Given a new Kalah game has started
      When Player One is sowing from house no. 1
      Then the playarea must contain 48 seeds
      And the playarea must exactly be:
        | O | H | H | H | H | H | H | T |
        | 0 | 4 | 5 | 5 | 5 | 5 | 0 |   |
        |   | 4 | 4 | 4 | 4 | 4 | 4 | 0 |

    Scenario: Player Two is sowing
      Given a new Kalah game has started
      When Player Two is sowing from house no. 1
      Then the playarea must contain 48 seeds
      And the playarea must exactly be:
        | O | H | H | H | H | H | H | T |
        | 0 | 4 | 4 | 4 | 4 | 4 | 4 |   |
        |   | 0 | 5 | 5 | 5 | 5 | 4 | 0 |

  Rule: storing is only allowed in your seedstore
    Scenario: Player One is sowing into his store
      Given a new Kalah game has started
      When Player One is sowing from house no. 4
      Then the playarea must contain 48 seeds
      And the playarea must exactly be:
        | O | H | H | H | H | H | H | T |
        | 1 | 5 | 5 | 0 | 4 | 4 | 4 |   |
        |   | 5 | 4 | 4 | 4 | 4 | 4 | 0 |

    Scenario: Player One is sowing into his store but not in Player Two
      Given a Kalah game was set up with
        | O | H | H | H | H | H | H | T |
        | 0 | 8 | 4 | 4 | 4 | 0 | 4 |   |
        |   | 4 | 4 | 4 | 4 | 4 | 4 | 0 |
      When Player One is sowing from house no. 6
      Then the playarea must contain 48 seeds
      And the playarea must exactly be:
        | O | H | H | H | H | H | H | T |
        | 1 | 0 | 4 | 4 | 4 | 0 | 5 |   |
        |   | 5 | 5 | 5 | 5 | 5 | 5 | 0 |

  Rule: Seeds need to be transferred in special cases
    Scenario: Take all seeds from the opponents house when a player ends in his empty house
      Given a Kalah game was set up with
        | O | H | H | H | H | H | H | T |
        | 4 | 0 | 4 | 4 | 4 | 4 | 4 |   |
        |   | 4 | 4 | 4 | 4 | 4 | 4 | 0 |
      When Player One is sowing from house no. 2
      Then the playarea must contain 48 seeds
      And the playarea must exactly be:
        | O | H | H | H | H | H | H | T |
        | 9 | 0 | 5 | 5 | 5 | 0 | 4 |   |
        |   | 0 | 4 | 4 | 4 | 4 | 4 | 0 |

    Scenario: When a player has no seeds left the other player collects all his seeds
      Given a Kalah game was set up with
        | B  | H | H | H | H | H | H | T |
        | 23 | 1 | 0 | 0 | 0 | 0 | 0 |   |
        |    | 4 | 4 | 4 | 4 | 4 | 4 | 0 |
      When Player One is sowing from house no. 6
      Then the playarea must exactly be:
        | B  | H | H | H | H | H | H | G  |
        | 24 | 0 | 0 | 0 | 0 | 0 | 0 |    |
        |    | 0 | 0 | 0 | 0 | 0 | 0 | 24 |
      And the playarea must contain 48 seeds
