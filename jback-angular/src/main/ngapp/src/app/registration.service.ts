import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { InitialRegistrationData } from './initial-registration-data';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor() { }

  postInitialRegistrationData(data: InitialRegistrationData) {
    console.log(data);
  }

}
