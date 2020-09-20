import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { SessionInfo } from './session-info';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private http: HttpClient) { }

  getSessionInfo(): Observable<SessionInfo> {
    return this.http.get<SessionInfo>("/api/v1/session");
  }

  postLogout(): Observable<void> {
    return this.http.post<void>("/api/v1/session/logout", null);
  }
}
