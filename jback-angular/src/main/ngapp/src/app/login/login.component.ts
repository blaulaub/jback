import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {
  FormGroup,
  Validators,
  FormBuilder,
  AbstractControl,
  ValidatorFn
} from '@angular/forms';

import { SessionService } from '../session/session.service';
import { LoginData } from '../session/login-data';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(
    private sessionService: SessionService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.loginForm = this.fb.group({
      username: this.fb.control(null, [ Validators.required ]),
      password: this.fb.control(null, [ Validators.required ]),
    });

  }

  ngOnInit(): void {
  }

  submit() {

    let loginData = new LoginData();
    loginData.userIdentification = this.loginForm.controls.username.value;
    loginData.verificationMean.username = this.loginForm.controls.username.value;
    loginData.verificationMean.password = this.loginForm.controls.password.value;

    this.sessionService.postLogin(loginData)
      .subscribe(() => this.router.navigate(["frontpage"]));
  }
}
