Feature: GameState needs to managed through the game
  As i Player i expect that i can play Kalah with a second player.
  The game starts as soon as there are to players in a game. Each
  Player then has to make moves each round until the game reaches
  an GameOver state. If the game is over i want to see who won.

  Scenario: Game was started and is waiting for players
    Given a game was started
    Then the game state is Awaiting Players

  Scenario: Game is still waiting after on player joined
    Given a game was started
    When a player joined the game
    Then the game state is Awaiting Players

  Scenario: Two players joined the game and it starts
    Given a game was started
    When a player joined the game
    And a player joined the game
    Then the game state is Ongoing

  Scenario: A player earns an extra round
    Given a game was ongoing with two players
    When player One move results in ExtraRound Event
    Then the game state is Ongoing
    And the next up is player One

  Scenario: A player earns a retry
    Given a game was ongoing with two players
    When player One move results in Retry Event
    Then the game state is Ongoing
    And the next up is player One

  Scenario: A player makes a move and the opponent will move next
    Given a game was ongoing with two players
    When player One move results in NextPlayer Event
    Then the game state is Ongoing
    And the next up is player Two

  Scenario: A player move ends the game
    Given a game was ongoing with two players
    When player One move results in GameOver Event
    Then the game state is Game Over


