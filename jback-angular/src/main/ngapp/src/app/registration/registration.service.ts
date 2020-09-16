import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { InitialRegistrationData } from './initial-registration-data';
import { PendingRegistrationInfo } from './pending-registration-info';
import { VerificationCode } from './verification-code';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  postInitialRegistrationData(data: InitialRegistrationData): Observable<PendingRegistrationInfo> {
    return this.http.post<PendingRegistrationInfo>("/api/v1/registration", data);
  }

  putVerificationCode(id: string, data: VerificationCode): Observable<void> {
    return this.http.put<void>("/api/v1/registration/" + id, data);
  }
}
