import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { FrontPageComponent } from './front-page/front-page.component';
import { RegisterComponent } from './registration/register/register.component';
import { CompleteComponent } from './registration/complete/complete.component';
import { CreateMeComponent } from './registration/create-me/create-me.component';
import { CreateChildComponent } from './registration/create-child/create-child.component';
import { ShowPersonComponent } from './person/show-person/show-person.component';
import { PersonActionsComponent } from './person/show-person/person-actions/person-actions.component';
import { CreateClubComponent } from './clubs/create-club/create-club.component';
import { BrowseClubsComponent } from './clubs/browse-clubs/browse-clubs.component';
import { ShowClubComponent } from './clubs/show-club/show-club.component';
import { ClubActionsComponent } from './clubs/show-club/club-actions/club-actions.component';
import { RequestMembershipComponent } from './memberships/request-membership/request-membership.component';
import { ShowApplicationsComponent } from './memberships/show-applications/show-applications.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    CompleteComponent,
    CreateMeComponent,
    FrontPageComponent,
    LoginComponent,
    CreateClubComponent,
    BrowseClubsComponent,
    ShowClubComponent,
    ShowPersonComponent,
    ClubActionsComponent,
    RequestMembershipComponent,
    PersonActionsComponent,
    CreateChildComponent,
    ShowApplicationsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
