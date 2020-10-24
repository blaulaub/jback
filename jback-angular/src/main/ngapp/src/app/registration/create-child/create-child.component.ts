import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import {
  FormGroup,
  Validators,
  FormArray,
  FormBuilder,
  AbstractControl,
  ValidatorFn
} from '@angular/forms';

import { PersonService } from '../../person/person.service';
import { Person } from '../../person/person';

@Component({
  selector: 'app-create-child',
  templateUrl: './create-child.component.html',
  styleUrls: ['./create-child.component.scss']
})
export class CreateChildComponent implements OnInit {

  private id: string;

  person: Person = null;

  childForm: FormGroup;

  constructor(
    private personService: PersonService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {

    this.childForm = this.fb.group({
      firstName: this.fb.control(null, [
        Validators.required
      ]),
      lastName: this.fb.control(null, [
        Validators.required
      ])
    });

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.personService.getPerson(this.id).subscribe(it => this.person = it);
  }

  submit(): void {
    this.personService.postPerson({
      firstName: this.childForm.controls.firstName.value,
      lastName: this.childForm.controls.lastName.value,
      address: []
    })
      .subscribe(() => this.router.navigate(['person', this.id]));
  }
}
