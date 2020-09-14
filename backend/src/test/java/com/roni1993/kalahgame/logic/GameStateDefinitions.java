package com.roni1993.kalahgame.logic;

import com.roni1993.kalahgame.api.EventingService;
import com.roni1993.kalahgame.model.GameState;
import com.roni1993.kalahgame.model.Player;
import com.roni1993.kalahgame.model.PlayerInfo;
import com.roni1993.kalahgame.model.RoundResult;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class GameStateDefinitions {

    private GameStateManager gameStateManager;
    private PlayArea playArea = Mockito.mock(PlayArea.class);
    private HashMap<Player, Integer> playerSeedstores = new HashMap<>();

    @ParameterType("Awaiting Players|Ongoing|Game Over")
    public GameState gameState(String gamestate) {
        switch (gamestate)
        {
            case "Awaiting Players":
                return GameState.AWAITING_PLAYER;
            case "Ongoing":
                return GameState.ONGOING;
            case "Game Over":
                return GameState.ENDED;
            default:
                return null;
        }
    }

    @Then("the game state is {gameState}")
    public void theGameStateIs(GameState expected) {
        assertEquals("Check gamestate", expected, gameStateManager.getCurrentState());
    }

    @Given("a game was started")
    public void aGameWasStarted() {
        gameStateManager = new GameStateManager(Mockito.mock(EventingService.class));
    }

    @When("a player joined the game")
    public void aPlayerJoinedTheGame() {
        gameStateManager.join(new PlayerInfo("One", "Blue"));
    }

    @Given("a game was ongoing with two players")
    public void aGameWasOngoingWithTwoPlayers() {
        gameStateManager = new GameStateManager(playArea, Mockito.mock(EventingService.class));
        gameStateManager.join(new PlayerInfo("One", "Blue"));
        gameStateManager.join(new PlayerInfo("Two", "Green"));
    }

    @When("player {player} move results in {roundResult} Event")
    public void playerPlayerMoveResultsInRoundResultEvent(Player player, RoundResult result) {
        gameStateManager.setCurrentPlayer(player);
        Mockito.when(playArea.sow(player,1)).thenReturn(result);
        gameStateManager.sow(player,1);
    }

    @And("the next up is player {player}")
    public void theNextUpIsPlayerPlayer(Player player) {
        assertEquals(player, gameStateManager.getCurrentPlayer());
    }

    @Given("Player {player} has {int} in the seedstore")
    public void playerOneHasFirstInTheSeedstore(Player player, int seeds) {
        Mockito.when(playArea.getSeedstore(player)).thenReturn(seeds);
    }

    @When("the game ends")
    public void theGameEnds() {
        //Intentionaly left empty as nothing has to be done due to mocks
    }

    @Then("the determined winner should be {player}")
    public void theDeterminedWinnerShouldBeWinner(Player player) {
        assertEquals(player, gameStateManager.calculateWinner());
    }
}
