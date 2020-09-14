package com.roni1993.kalahgame.logic;

import com.roni1993.kalahgame.model.Player;
import com.roni1993.kalahgame.model.RoundResult;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.common.collect.Lists;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class GameLogicDefinitions {
    PlayArea current;
    private RoundResult sowingResult;

    @ParameterType("One|Two|None")
    public Player player(String player) {
        return Player.valueOf(player);
    }

    @ParameterType("ExtraRound|Retry|GameOver|NextPlayer")
    public RoundResult roundResult(String roundResult) {
        return RoundResult.valueOf(roundResult);
    }

    @Given("a new Kalah game has started")
    public void aNewKalahGameHasStarted() {
        current = new PlayArea();
    }

    @Then("each Player should have {int} houses")
    public void eachPlayerShouldHaveHouses(int arg0) {
        assertEquals("Each player should have a speciefied aount of houses", arg0,
                current.getHouses(Player.One).size());
        assertEquals("Each player should have a speciefied aount of houses", arg0,
                current.getHouses(Player.Two).size());
    }

    @Then("each Player should have a seedstore")
    public void eachPlayerShouldHaveASeedstore() {
        assertNotNull("Each player should have a seedstore", current.getSeedstore(Player.One));
        assertNotNull("Each player should have a seedstore", current.getSeedstore(Player.Two));
    }

    @Then("the playarea must contain {int} seeds")
    public void thePlayareaMustContainSeeds(int expected) {
        int seeds = Stream.of(current.getAllHouses(Player.One),current.getAllHouses(Player.Two))
                .flatMap(Collection::stream)
                .mapToInt(Integer::intValue)
                .sum();
        assertEquals("the Playarea seedcount must match the expected", expected, seeds);
    }

    @Given("a Kalah game was set up with")
    public void aKalahGameWasSetUpWith(DataTable values) {
        var playerOne = getMutableInts(Lists.reverse(values.row(1)));
        var playerTwo = getMutableInts(values.row(2));

        current = new PlayArea(playerOne.get(6), playerTwo.get(6),
                playerOne.subList(0, 6), playerTwo.subList(0, 6));
    }

    @Then("the playarea must exactly be:")
    public void thePlayareaMustExactlyBe(DataTable values) {
        var expectedOne = getInts(Lists.reverse(values.row(1)));
        var expectedTwo = getInts(values.row(2));

        var housesOne = current.getAllHouses(Player.One);
        var housesTwo = current.getAllHouses(Player.Two);
        assertEquals("the actual houses have to be equal to the expected", expectedOne, housesOne);
        assertEquals("the actual houses have to be equal to the expected", expectedTwo, housesTwo);
    }

    private List<MutableInt> getMutableInts(List<String> playerList) {
        return playerList.stream()
                .skip(1)
                .map(MutableInt::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Integer> getInts(List<String> playerList) {
        return playerList.stream()
                .skip(1)
                .map(Integer::parseInt)
                .collect(Collectors.toUnmodifiableList());
    }

    @When("Player {player} is sowing from house no. {int}")
    public void playerIsSowingFromHouseNo(Player player, int house) {
        sowingResult = current.sow(player, house);
    }

    @Then("the Round result should be {roundResult}")
    public void theRoundResultShouldBe(RoundResult expected) {
        assertEquals("", expected, sowingResult);
    }
}
