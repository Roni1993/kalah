package com.roni1993.kalahgame.dto;

import com.roni1993.kalahgame.model.Player;
import com.roni1993.kalahgame.model.PlayerInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class PlayerDto {
    @NonNull UUID gameId;
    @NonNull Player position;
    @NonNull String token;
}
