import { Component, OnDestroy, OnInit } from '@angular/core';
import { Configuration, GameControllerService, GameDto, PlayerDto, PlayerInfo } from 'src/app/api';
import { apiConfig, environment, player } from 'src/environments/environment';
import { Router } from '@angular/router';
import { EventingService } from "src/app/service/eventing.service";


@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit, OnDestroy {
  games:GameDto[] = []

  playerInfo:PlayerInfo = {
    name: "",
    color: ""
  }
  
  constructor(private api:GameControllerService,
              private eventing: EventingService,
              private router: Router) { }

  ngOnInit(): void {
    this.api.configuration = new Configuration(apiConfig);
    this.getGames()

    //set up eventing
    this.eventing.games.subscribe(result => {
      this.getGames();
    })
    this.eventing.connect() //connects WS and emits events
  }

  ngOnDestroy(): void {
    this.eventing.disconnect()
  }

  createGame() {
    this.api.createGame().subscribe()
  }

  getGames() {
    this.api.getGames().subscribe(games => this.games = games)
  }

  joinGame(id:string) {
    this.api.joinGame(id,this.playerInfo).subscribe(playerInfo => {
        apiConfig.accessToken = playerInfo.token;
        player.gameId = playerInfo.gameId
        player.position = playerInfo.position
        player.token = playerInfo.token
        this.router.navigate(['/game'])
      })
  }
}

