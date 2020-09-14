package com.roni1993.kalahgame.dto;

import com.roni1993.kalahgame.model.Player;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PlayAreaDto {
    @NonNull Player currentPlayer;
    @NonNull Integer seedstorePlayerOne;
    @NonNull Integer seedstorePlayerTwo;
    @NonNull List<Integer> housesPlayerOne;
    @NonNull List<Integer> housesPlayerTwo;
}
