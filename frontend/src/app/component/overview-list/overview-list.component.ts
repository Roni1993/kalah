import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { GameDto } from 'src/app/api';

@Component({
  selector: 'app-overview-list',
  templateUrl: './overview-list.component.html',
  styleUrls: ['./overview-list.component.css']
})
export class OverviewListComponent implements OnInit {
  @Input() games:GameDto[]
  @Output() joinGame:EventEmitter<string> = new EventEmitter();
  
  constructor() { }

  ngOnInit(): void {
  }

}