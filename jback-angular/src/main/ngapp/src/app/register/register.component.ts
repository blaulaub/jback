import { Component, OnInit } from '@angular/core';

import { RegistrationService } from '../registration/registration.service';

import { InitialRegistrationData } from '../registration/initial-registration-data';
import { VerificationMean } from '../registration/verification-mean';
import { VerificationByEmail } from '../registration/verification-by-email';
import { VerificationBySms } from '../registration/verification-by-sms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  readonly verificationMeans = [ new VerificationByEmail(), new VerificationBySms() ];

  model = new InitialRegistrationData();

  constructor(private registrationService: RegistrationService) { }

  ngOnInit(): void {
  }

  isSms() : boolean {
    return this.model.verificationMean instanceof VerificationBySms;
  }

  isEmail() : boolean {
    return this.model.verificationMean instanceof VerificationByEmail;
  }

  canSubmit(): boolean {
    return this.model.isValid();
  }

  submit() {
    this.registrationService.postInitialRegistrationData(this.model)
      .subscribe(result => console.log(result));
  }
}
