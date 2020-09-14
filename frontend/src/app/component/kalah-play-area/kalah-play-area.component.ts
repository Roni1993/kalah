import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Configuration, GameControllerService, GameDto, PlayAreaDto } from 'src/app/api';
import { apiConfig, player } from 'src/environments/environment';
import { EventingService, GameStateEnum, RoundResultEnum } from "src/app/service/eventing.service";

@Component({
  selector: 'app-kalah-play-area',
  templateUrl: './kalah-play-area.component.html',
  styleUrls: ['./kalah-play-area.component.css']
})
export class KalahPlayAreaComponent implements OnInit, OnDestroy {
  private roundListener = new Subject<string>();
  state: GameDto
  playarea: PlayAreaDto
  alerts = []
  localPlayer = player.position

  constructor(private api: GameControllerService,
    private eventing: EventingService,
    private router: Router) { }

  ngOnDestroy(): void {
    this.eventing.disconnect()
  }

  ngOnInit(): void {
    this.roundListener.subscribe(message => this.alerts.push(message));
    this.roundListener.pipe(delay(10000)).subscribe(() => this.alerts.shift());

    //API Init
    this.api.configuration = new Configuration(apiConfig);
    if (apiConfig.accessToken == null) {
      this.router.navigate(['/overview'])
      return
    }
    this.getPlayArea()
    this.getGameState()

    //set up eventing
    this.eventing.rounds.subscribe(result => {
      this.newRoundResult(result);
    })
    this.eventing.stats.subscribe(result => {
      this.newGameState(result);
    })
    this.eventing.connect() //connects WS and emits events
  }

  getPlayArea() {
    this.api.getPlayArea(player.gameId).subscribe(response => this.playarea = response)
  }

  getGameState() {
    this.api.getGamestate(player.gameId).subscribe(response => this.state = response)
  }

  public newRoundResult(round: RoundResultEnum) {
    this.roundListener.next(`A player has made a move: ${round}`);
    this.getPlayArea() // get updates
  }

  public newGameState(state: GameStateEnum) {
    this.roundListener.next(`The Game State has updated: ${state}`);
    this.getGameState() // get updates
  }

  sowHouse(house: number, housePlayer: string) {
    this.api.sow(player.gameId, house).subscribe()
  }
}
