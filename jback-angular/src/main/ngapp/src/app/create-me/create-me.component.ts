import { Component, OnInit } from '@angular/core';

import { PersonService } from '../person/person.service';
import { PersonDraft } from '../person/person-draft';

@Component({
  selector: 'app-create-me',
  templateUrl: './create-me.component.html',
  styleUrls: ['./create-me.component.scss']
})
export class CreateMeComponent implements OnInit {

  model = new PersonDraft();

  constructor(private personService: PersonService) { }

  ngOnInit(): void {
  }

  canSubmit(): boolean {
    return this.model.isValid();
  }

  submit() {
    this.personService.postCreateOwnPerson(this.model)
      .subscribe(result => console.log(result));
  }
}
