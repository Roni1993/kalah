package com.roni1993.kalahgame.logic;

import com.roni1993.kalahgame.dto.PlayAreaDto;
import com.roni1993.kalahgame.model.Player;
import com.roni1993.kalahgame.model.RoundResult;
import lombok.NonNull;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayArea {
    public static final int SEEDSTORE_INDEX = 6;

    private final List<MutableInt> housesPlayerOne;
    private final List<MutableInt> housesPlayerTwo;

    private final List<MutableInt> roundPlayerOne;
    private final List<MutableInt> roundPlayerTwo;

    public PlayArea() {
        this(new MutableInt(0), new MutableInt(0), mutableIntList(4, 4, 4, 4, 4, 4), mutableIntList(4, 4, 4, 4, 4, 4));
    }

    public PlayArea(@NonNull MutableInt seedstorePlayerOne, @NonNull MutableInt seedstorePlayerTwo, @NonNull List<MutableInt> fieldsPlayerOne, @NonNull List<MutableInt> fieldsPlayerTwo) {
        housesPlayerOne = concatList(fieldsPlayerOne, List.of(seedstorePlayerOne));
        housesPlayerTwo = concatList(fieldsPlayerTwo, List.of(seedstorePlayerTwo));

        roundPlayerOne = concatList(fieldsPlayerOne, List.of(seedstorePlayerOne), fieldsPlayerTwo);
        roundPlayerTwo = concatList(fieldsPlayerTwo, List.of(seedstorePlayerTwo), fieldsPlayerOne);
    }

    @SafeVarargs
    static List<MutableInt> concatList(List<MutableInt>... lists) {
        return Stream.of(lists)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    static List<MutableInt> mutableIntList(int... integer) {
        return Arrays.stream(integer)
                .mapToObj(MutableInt::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public RoundResult sow(Player player, int houseNumber) {
        int index = houseNumber - 1;
        if (Player.One.equals(player)) {
            return sow(Player.Two, roundPlayerOne, index);
        } else if (Player.Two.equals(player)) {
            return sow(Player.One, roundPlayerTwo, index);
        }
        return RoundResult.Invalid;
    }

    private RoundResult sow(Player opponent, List<MutableInt> playerList, int index) {
        int seeds = setSeedsAtHouse(playerList, index, 0);

        // retry if house has no seeds
        if (seeds == 0) {
            return RoundResult.Retry;
        }

        // Pass around seeds
        int lastHouseIndex = index;
        MutableInt lastHouse = null;
        for (int i = 1; i <= seeds; i++) {
            lastHouseIndex = (index + i) % playerList.size();
            lastHouse = playerList.get(lastHouseIndex);
            lastHouse.increment();
        }

        //steal seeds if last house was empty
        if (lastHouse.getValue() == 1) {
            // take seeds
            lastHouse.setValue(0);
            int opponentSeeds = setSeedsAtHouse(playerList, playerList.size() - (lastHouseIndex + 1), 0);

            //place into store
            playerList.get(SEEDSTORE_INDEX).add(opponentSeeds + 1);
        }

        // Validate whether the Game is Over
        int seedsInGame = playerList.stream().limit(6).mapToInt(MutableInt::getValue).sum();
        if (seedsInGame == 0) {
            List<MutableInt> opponentHouses = getAllHousesMutable(opponent);
            int opponentSeeds = opponentHouses.stream().limit(6).mapToInt(MutableInt::getValue).sum();
            opponentHouses.forEach(house -> house.setValue(0));
            setSeedsAtHouse(opponentHouses, SEEDSTORE_INDEX, opponentSeeds);

            return RoundResult.GameOver;
        }

        // extra round when ending in seedstore
        return (lastHouseIndex == SEEDSTORE_INDEX)
                ? RoundResult.ExtraRound
                : RoundResult.NextPlayer;
    }

    private static int setSeedsAtHouse(List<MutableInt> playerList, int houseNumber, int newSeeds) {
        MutableInt house = playerList.get(houseNumber);
        int seeds = house.getValue();
        house.setValue(newSeeds);
        return seeds;
    }

    public List<Integer> getHouses(Player player) {
        return getAllHousesMutable(player).stream().map(MutableInt::getValue).limit(6).collect(Collectors.toList());
    }

    public Integer getSeedstore(Player player) {
        return getAllHousesMutable(player).get(SEEDSTORE_INDEX).getValue();
    }

    private List<MutableInt> getAllHousesMutable(Player player) {
        Objects.requireNonNull(player);
        return Player.One.equals(player)
                ? housesPlayerOne
                : housesPlayerTwo;
    }

    public List<Integer> getAllHouses(Player player) {
        return getAllHousesMutable(player).stream().map(MutableInt::getValue).collect(Collectors.toList());
    }

    public PlayAreaDto getDto(Player currentPlayer) {
        return PlayAreaDto.builder()
                .currentPlayer(currentPlayer)
                .seedstorePlayerOne(getSeedstore(Player.One))
                .seedstorePlayerTwo(getSeedstore(Player.Two))
                .housesPlayerOne(getHouses(Player.One))
                .housesPlayerTwo(getHouses(Player.Two))
                .build();
    }
}