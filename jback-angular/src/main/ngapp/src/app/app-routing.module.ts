import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { CompleteComponent } from './complete/complete.component';
import { CreateMeComponent } from './create-me/create-me.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'complete/:id', component: CompleteComponent },
  { path: 'createMe', component: CreateMeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
