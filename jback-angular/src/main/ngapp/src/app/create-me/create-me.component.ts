import { Component, OnInit } from '@angular/core';

import { PersonDraft } from '../person/person-draft';

@Component({
  selector: 'app-create-me',
  templateUrl: './create-me.component.html',
  styleUrls: ['./create-me.component.scss']
})
export class CreateMeComponent implements OnInit {

  model = new PersonDraft();

  constructor() { }

  ngOnInit(): void {
  }

  canSubmit(): boolean {
    return this.model.isValid();
  }

  submit() {
    console.log("creating me");
  }
}
