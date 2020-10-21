import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { FrontPageComponent } from './front-page/front-page.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './registration/register/register.component';
import { CompleteComponent } from './registration/complete/complete.component';
import { CreateMeComponent } from './registration/create-me/create-me.component';
import { CreateClubComponent } from './clubs/create-club/create-club.component';
import { BrowseClubsComponent } from './clubs/browse-clubs/browse-clubs.component';
import { ShowClubComponent } from './clubs/show-club/show-club.component';
import { ShowPersonComponent } from './person/show-person/show-person.component';

const routes: Routes = [
  { path: '', redirectTo: '/frontpage', pathMatch: 'full' },
  { path: 'frontpage', component: FrontPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'complete/:id', component: CompleteComponent },
  { path: 'createMe', component: CreateMeComponent },
  { path: 'createClub', component: CreateClubComponent },
  { path: 'browseClubs', component: BrowseClubsComponent },
  { path: 'club/:id', component: ShowClubComponent },
  { path: 'person/:id', component: ShowPersonComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
