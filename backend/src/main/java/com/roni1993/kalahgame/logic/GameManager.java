package com.roni1993.kalahgame.logic;

import com.roni1993.kalahgame.api.EventingService;
import com.roni1993.kalahgame.dto.GameDto;
import com.roni1993.kalahgame.model.Player;
import com.roni1993.kalahgame.model.PlayerInfo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Getter
public class GameManager {
    HashMap<UUID,GameStateManager> games = new HashMap<>();

    @Autowired
    EventingService eventingService;

    public GameDto createGame()
    {
        var gameStateManager = new GameStateManager(eventingService);
        var gameId = gameStateManager.getGameId();
        games.put(gameId,gameStateManager);
        return gameStateManager.getGameDto();
    }

    public List<GameDto> getGames()
    {
        return games.values().stream().map(GameStateManager::getGameDto).collect(Collectors.toList());
    }

    public Player joinGame(UUID id, PlayerInfo player) {
        var game = games.get(id);
        return game.join(player);
    }

    public GameStateManager getGame(UUID id) {
        return games.get(id);
    }
}
