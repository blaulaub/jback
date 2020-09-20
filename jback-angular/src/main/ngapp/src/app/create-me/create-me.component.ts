import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { PersonService } from '../person/person.service';
import { PersonDraft } from '../person/person-draft';
import { SessionService } from '../session/session.service';

@Component({
  selector: 'app-create-me',
  templateUrl: './create-me.component.html',
  styleUrls: ['./create-me.component.scss']
})
export class CreateMeComponent implements OnInit {

  model: PersonDraft = {
    firstName: null,
    lastName: null,
    address: [ null ]
  };

  constructor(
    private personService: PersonService,
    private sessionService: SessionService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.sessionService.getSessionInfo()
    .subscribe(result => {
      this.model.firstName = result.firstName;
      this.model.lastName = result.lastName;
    })
  }

  canSubmit(): boolean {
    return PersonDraft.isValid(this.model);
  }

  submit() {
    this.personService.postCreateOwnPerson(this.model)
      .subscribe(() => this.router.navigate(["frontpage"]));
  }

  trackByIdx(index: number, obj: any): any {
    return index;
  }

  addLine() {
    this.model.address.push(null);
  }

  removeLine(i: number) {
    this.model.address.splice(i, 1);
  }
}
