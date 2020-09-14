import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { ApiModule, BASE_PATH } from './api/';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CustomHttpInterceptor } from "src/app/interceptor/custom-http-interceptor";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OverviewComponent } from './page/overview/overview.component';
import { GameComponent } from './page/game/game.component';
import { GameOverComponent } from './modal/game-over/game-over.component';
import { KalahPlayAreaComponent } from './component/kalah-play-area/kalah-play-area.component';
import { OverviewListComponent } from './component/overview-list/overview-list.component';

@NgModule({
  declarations: [
    AppComponent,
    OverviewComponent,
    GameComponent,
    GameOverComponent,
    KalahPlayAreaComponent,
    OverviewListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    ApiModule
  ],
  providers: [
    {
      provide: BASE_PATH,
      useValue: environment.basePath
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomHttpInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
