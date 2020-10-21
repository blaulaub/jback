import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { AppRoutingModule } from './app-routing.module';
import { CompleteComponent } from './complete/complete.component';
import { CreateMeComponent } from './create-me/create-me.component';
import { FrontPageComponent } from './front-page/front-page.component';
import { LoginComponent } from './login/login.component';
import { CreateClubComponent } from './create-club/create-club.component';
import { BrowseClubsComponent } from './browse-clubs/browse-clubs.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    CompleteComponent,
    CreateMeComponent,
    FrontPageComponent,
    LoginComponent,
    CreateClubComponent,
    BrowseClubsComponent
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
