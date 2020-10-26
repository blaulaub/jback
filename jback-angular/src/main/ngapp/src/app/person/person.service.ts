import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { PersonDraft } from './person-draft';
import { PersonWithPasswordDraft } from './person-with-password-draft';
import { Person } from './person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http: HttpClient) { }

  getPerson(id: string): Observable<Person> {
    return this.http.get<Person>(`/api/v1/persons/${id}`);
  }

  getPersonForCurrentPrincipal(): Observable<Person[]> {
    return this.http.get<Person[]>(`/api/v1/persons/for-current-principal`);
  }

  postPerson(data: PersonDraft): Observable<Person> {
    return this.http.post<Person>('/api/v1/persons', data);
  }

  postPersonWithPassword(data: PersonWithPasswordDraft): Observable<Person> {
    return this.http.post<Person>('/api/v1/persons/with-password', data);
  }
}
