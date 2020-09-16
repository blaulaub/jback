import { Component, OnInit } from '@angular/core';

import { PersonService } from '../person/person.service';
import { PersonDraft } from '../person/person-draft';

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

  constructor(private personService: PersonService) { }

  ngOnInit(): void {
  }

  canSubmit(): boolean {
    return PersonDraft.isValid(this.model);
  }

  submit() {
    this.personService.postCreateOwnPerson(this.model)
      .subscribe(result => console.log(result));
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
