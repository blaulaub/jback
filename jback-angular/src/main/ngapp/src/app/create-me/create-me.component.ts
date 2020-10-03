import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormArray, FormBuilder } from '@angular/forms';

import { PersonService } from '../person/person.service';
import { PersonDraft } from '../person/person-draft';
import { SessionService } from '../session/session.service';

@Component({
  selector: 'app-create-me',
  templateUrl: './create-me.component.html',
  styleUrls: ['./create-me.component.scss']
})
export class CreateMeComponent implements OnInit {

  personForm: FormGroup;

  constructor(
    private personService: PersonService,
    private sessionService: SessionService,
    private router: Router,
    private fb: FormBuilder
    ) {

      this.personForm = this.fb.group({
        firstName: this.fb.control(null, [
          Validators.required
        ]),
        lastName: this.fb.control(null, [
          Validators.required
        ]),
        address: this.fb.array([this.fb.control(null, [
          Validators.required,
        ])]),
        username: this.fb.control(null, [
          Validators.required,
          Validators.minLength(3)
        ]),
        password: this.fb.control(null, [
          Validators.required,
          Validators.minLength(8)
        ])
      });  
    }

  ngOnInit(): void {

    this.sessionService.getSessionInfo()
    .subscribe(result => {
      this.personForm.controls.firstName.setValue(result.firstName);
      this.personForm.controls.lastName.setValue(result.lastName);
    });
  }

  get address(): FormArray {
    return this.personForm.controls.address as FormArray;
  }

  submit() {
    this.personService.postCreateOwnPerson({
      firstName: this.personForm.controls.firstName.value,
      lastName: this.personForm.controls.lastName.value,
      address: this.personForm.controls.address.value,
      username: this.personForm.controls.username.value,
      password: this.personForm.controls.password.value
    })
      .subscribe(() => this.router.navigate(["frontpage"]));
  }

  addLine() {
    this.address.controls.push(this.fb.control( null,
      [
        Validators.required,
      ]));
  }

  removeLine(i: number) {
    this.address.removeAt(i);
  }
}
