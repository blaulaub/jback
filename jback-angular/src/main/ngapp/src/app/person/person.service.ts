import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { PersonDraft } from './person-draft';
import { ch.patchcode.jback.core.entities.Person } from './person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http: HttpClient) { }

  postCreateOwnPerson(data: PersonDraft): Observable<ch.patchcode.jback.core.entities.Person> {
    return this.http.post<ch.patchcode.jback.core.entities.Person>("/api/v1/persons/me", data);
  }
}
