import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ClubDraft } from './club-draft';
import { Club } from './club';
import { ClubMembershipAppicationDraft } from './club-membership-application-draft';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  constructor(private http: HttpClient) { }

  getClub(id: string): Observable<Club> {

    return this.http.get<Club>(`/api/v1/clubs/${id}`);
  }

  searchClubs(term: string): Observable<Club[]> {

    if (!term.trim()) {
      return of([]);
    }

    return this.http.get<Club[]>(`/api/v1/clubs?pattern=${term}`);
  }

  postCreateClub(data: ClubDraft): Observable<Club> {
    return this.http.post<Club>('/api/v1/clubs', data);
  }

  postMembershipApplication(id: string, data: ClubMembershipAppicationDraft): Observable<void> {
    return this.http.post<void>(`/api/v1/clubs/${id}/membership-applications`, data);
  }

}
