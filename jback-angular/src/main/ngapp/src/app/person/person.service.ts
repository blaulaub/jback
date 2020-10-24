import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

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

  postPersonWithPassword(data: PersonWithPasswordDraft): Observable<Person> {
    return this.http.post<Person>('/api/v1/persons/with-password', data);
  }
}
