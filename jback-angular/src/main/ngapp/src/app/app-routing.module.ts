import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { CompleteComponent } from './complete/complete.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'complete/:id', component: CompleteComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
