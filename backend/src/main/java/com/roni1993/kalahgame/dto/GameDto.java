package com.roni1993.kalahgame.dto;

import com.roni1993.kalahgame.model.GameState;
import com.roni1993.kalahgame.model.Player;
import com.roni1993.kalahgame.model.PlayerInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.UUID;

@Value
@Builder
public class GameDto {
    @NonNull UUID gameId;
    @NonNull GameState state;
    @NonNull Player winner;
    PlayerInfo playerOne;
    PlayerInfo playerTwo;
}