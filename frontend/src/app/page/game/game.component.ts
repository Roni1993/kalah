import { Component, OnInit } from '@angular/core';
import { Configuration, GameControllerService, GameDto, PlayAreaDto } from 'src/app/api';
import { apiConfig, player } from 'src/environments/environment';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  
  ngOnInit(): void {
  }

}
