import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ClubDraft } from './club-draft';
import { Club } from './club';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  constructor(private http: HttpClient) { }

  searchClubs(term: String): Observable<Club[]> {

    if (!term.trim()) {
      return of([])
    }

    return this.http.get<Club[]>(`/api/v1/clubs?pattern=${term}`)
  }

  postCreateClub(data: ClubDraft): Observable<Club> {
    return this.http.post<Club>("/api/v1/clubs", data)
  }

}
