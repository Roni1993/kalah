package com.roni1993.kalahgame.model;

public class GameRuleViolationException extends IllegalArgumentException{
    public GameRuleViolationException() {
    }

    public GameRuleViolationException(String s) {
        super(s);
    }

    public GameRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameRuleViolationException(Throwable cause) {
        super(cause);
    }
}
