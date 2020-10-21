import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { PersonDraft } from './person-draft';
import { Person } from './person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http: HttpClient) { }

  getPerson(id: String): Observable<Person> {

    return this.http.get<Person>(`/api/v1/persons/${id}`)
  }

  postCreateOwnPerson(data: PersonDraft): Observable<Person> {
    return this.http.post<Person>("/api/v1/persons/me", data);
  }
}
