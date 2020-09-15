import { Component, OnInit } from '@angular/core';

import { RegistrationService } from '../registration.service';

import { InitialRegistrationData } from '../initial-registration-data';
import { VerificationMean } from '../verification-mean';
import { VerificationByEmail } from '../verification-by-email';
import { VerificationBySms } from '../verification-by-sms';

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
    this.registrationService.postInitialRegistrationData(this.model);
  }
}
