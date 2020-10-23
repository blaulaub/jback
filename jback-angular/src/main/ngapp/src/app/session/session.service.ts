import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

import { SessionInfo } from './session-info';
import { LoginData } from './login-data';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private http: HttpClient) { }

  getSessionInfo(): Observable<SessionInfo> {
    return this.http.get<SessionInfo>('/api/v1/session');
  }

  postLogin(loginData: LoginData): Observable<void> {
    return this.http.post<void>('/api/v1/session/login', loginData);
  }

  postLogout(): Observable<void> {
    return this.http.post<void>('/api/v1/session/logout', null);
  }
}
