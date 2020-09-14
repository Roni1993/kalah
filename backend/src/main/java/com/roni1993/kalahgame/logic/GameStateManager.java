package com.roni1993.kalahgame.logic;

import com.roni1993.kalahgame.api.EventingService;
import com.roni1993.kalahgame.dto.GameDto;
import com.roni1993.kalahgame.dto.PlayAreaDto;
import com.roni1993.kalahgame.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Getter
public class GameStateManager {
    private final UUID gameId = UUID.randomUUID();

    EventingService eventing;

    @Setter
    private Player currentPlayer = Player.One;
    private final HashMap<Player, PlayerInfo> players = new HashMap<>();

    @Getter(AccessLevel.NONE)
    private PlayArea playArea = new PlayArea();
    private GameState currentState = GameState.AWAITING_PLAYER;

    public GameStateManager(EventingService eventing) {
        this.eventing = eventing;
    }

    GameStateManager(PlayArea playArea, EventingService eventingService) {
        this.playArea = playArea;
        this.eventing = eventingService;
    }

    public Player join(PlayerInfo newPlayer) {
        if (!GameState.AWAITING_PLAYER.equals(currentState))
            throw new GameRuleViolationException("This game already has two players");

        int size = players.size();
        if (size == 0) {
            players.put(Player.One, newPlayer);
            eventing.notifyGameState(gameId, currentState);
            return Player.One;
        } else {
            players.put(Player.Two, newPlayer);
            setGameState(GameState.ONGOING);
            return Player.Two;
        }
    }

    public void sow(Player callingPlayer, int houseNumber) {
        if (!Objects.equals(currentPlayer, callingPlayer))
            throw new GameRuleViolationException("Caller is not the Player with has the current turn");
        if (houseNumber < 1)
            throw new GameRuleViolationException("Housenumber has to be 1 or above");

        RoundResult result = playArea.sow(currentPlayer, houseNumber);
        eventing.notifyRoundResult(gameId, result);

        if (Objects.equals(RoundResult.NextPlayer, result))
            currentPlayer = Player.One.equals(currentPlayer) ? Player.Two : Player.One;
        if (Objects.equals(RoundResult.GameOver, result))
            setGameState(GameState.ENDED);
    }

    private void setGameState(GameState newState) {
        currentState = newState;
        eventing.notifyGameState(gameId, currentState);
    }

    public Player calculateWinner() {
        var seedsPlayerOne = playArea.getSeedstore(Player.One);
        var seedsPlayerTwo = playArea.getSeedstore(Player.Two);

        if (Objects.equals(seedsPlayerOne, seedsPlayerTwo)) {
            return Player.None;
        }
        return (seedsPlayerOne > seedsPlayerTwo) ? Player.One : Player.Two;
    }

    public GameDto getGameDto() {
        return GameDto.builder()
                .gameId(gameId)
                .state(currentState)
                .playerOne(players.get(Player.One))
                .playerTwo(players.get(Player.Two))
                .winner(calculateWinner())
                .build();
    }

    public PlayAreaDto getPlayAreaDto() {
        return playArea.getDto(currentPlayer);
    }
}

