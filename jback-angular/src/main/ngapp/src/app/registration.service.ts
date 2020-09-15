import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { InitialRegistrationData } from './initial-registration-data';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  postInitialRegistrationData(data: InitialRegistrationData) {
    console.log(data);
  }

}
