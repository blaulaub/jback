import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ClubDraft } from './club-draft';
import { Club } from './club';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  constructor(private http: HttpClient) { }

  postCreateClub(data: ClubDraft): Observable<Club> {
    return this.http.post<Club>("/api/v1/clubs", data);
  }

}
