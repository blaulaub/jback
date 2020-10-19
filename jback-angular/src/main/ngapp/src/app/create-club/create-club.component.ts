import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormGroup,
  Validators,
  FormBuilder,
  AbstractControl
} from '@angular/forms';

import { ClubService } from '../clubs/club.service';

@Component({
  selector: 'app-create-club',
  templateUrl: './create-club.component.html',
  styleUrls: ['./create-club.component.scss']
})
export class CreateClubComponent implements OnInit {

  clubForm: FormGroup;

  constructor(
    private clubService: ClubService,
    private router: Router,
    private fb: FormBuilder
  ) {

    this.clubForm = this.fb.group({
      name: this.fb.control(null, [
        Validators.required
      ]),
      url: this.fb.control(null, [])
    });  
  }

  ngOnInit(): void {
  }

  submit() {
    this.clubService.postCreateClub({
      name: this.clubForm.controls.name.value,
      url: this.clubForm.controls.url.value,
      contact: null
    })
      .subscribe(() => this.router.navigate(["frontpage"]));
  }

}
