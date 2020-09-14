package com.roni1993.kalahgame.api;

import com.roni1993.kalahgame.model.GameState;
import com.roni1993.kalahgame.model.RoundResult;
import lombok.extern.flogger.Flogger;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventingService {

    Logger logger = LoggerFactory.getLogger(EventingService.class);
    @Autowired
    SimpMessagingTemplate msgTemplate;

    public void notifyGameState(UUID gameId, GameState state) {
        notifyGame(state);
        var destination = String.format("/topic/game/%s/state",gameId);
        logger.info("{} {}",destination, state);
        msgTemplate.convertAndSend(destination,state);
    }

    public void notifyRoundResult(UUID gameId, RoundResult result) {
        var destination = String.format("/topic/game/%s/roundresults",gameId);
        logger.info("{} {}",destination, result);
        msgTemplate.convertAndSend(destination,result);
    }

    public void notifyGame( GameState game) {
        var destination = "/topic/overview";
        logger.info("{} {}",destination, game);
        msgTemplate.convertAndSend(destination,game);
    }
}
