import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { FrontPageComponent } from './front-page/front-page.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { CompleteComponent } from './complete/complete.component';
import { CreateMeComponent } from './create-me/create-me.component';
import { CreateClubComponent } from './create-club/create-club.component';
import { BrowseClubsComponent } from './browse-clubs/browse-clubs.component';
import { ShowClubComponent } from './show-club/show-club.component';

const routes: Routes = [
  { path: '', redirectTo: '/frontpage', pathMatch: 'full' },
  { path: 'frontpage', component: FrontPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'complete/:id', component: CompleteComponent },
  { path: 'createMe', component: CreateMeComponent },
  { path: 'createClub', component: CreateClubComponent },
  { path: 'browseClubs', component: BrowseClubsComponent },
  { path: 'club/:id', component: ShowClubComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
