import { EventEmitter, Injectable, Output } from '@angular/core';
import { environment, player } from 'src/environments/environment';
import * as Stomp from 'stompjs';

export type RoundResultEnum = 'NextPlayer' | 'ExtraRound' | 'Retry' | 'GameOver' | 'Invalid';
export type GameStateEnum = 'AWAITING_PLAYER' | 'ONGOING' | 'ENDED';

interface Listener {
  (message: string): void;
}

@Injectable({
  providedIn: 'root'
})
export class EventingService {
  @Output() rounds:EventEmitter<string> = new EventEmitter()
  @Output() stats:EventEmitter<string> = new EventEmitter()
  @Output() games:EventEmitter<string> = new EventEmitter()

  webSocket: WebSocket 
  client: Stomp.Client

  constructor() { }

  connect() {
    if(!this.client?.connected) {
      this.webSocket = new WebSocket(environment.websocket)
      this.client  = Stomp.over(this.webSocket)
    }
    this.subscribe()
  }

  subscribe() {
    this.client.connect({}, () => {
      this.client.subscribe(`/topic/game/${player.gameId}/roundresults`, round => {
        this.rounds.emit(JSON.parse(round.body))
      });

      this.client.subscribe(`/topic/game/${player.gameId}/state`, state => {
        this.stats.emit(JSON.parse(state.body))
      });

      this.client.subscribe(`/topic/overview`, state => {
        this.games.emit(JSON.parse(state.body))
      });
    })
  }

  disconnect() {
    this.client.disconnect(() => true)
    this.client = null
    this.webSocket.close()
    this.webSocket = null
  }
}
