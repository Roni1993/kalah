package com.roni1993.kalahgame.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInfo {
    @NonNull
    private String name;
    @NonNull
    private String color;
}
