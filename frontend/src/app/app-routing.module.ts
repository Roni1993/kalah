import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OverviewComponent } from "src/app/page/overview/overview.component";
import { GameComponent } from "src/app/page/game/game.component";

const routes: Routes = [
  { path: 'game', component: GameComponent },
  { path: 'overview', component: OverviewComponent },
  { path: '',   redirectTo: '/overview', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
