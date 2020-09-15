import { Component, OnInit } from '@angular/core';

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

  readonly methodOptions = [ new VerificationByEmail(), new VerificationBySms() ];

  model = new InitialRegistrationData();

  constructor() { }

  ngOnInit(): void {
  }

  isSms() : boolean {
    return this.model.verificationMean instanceof VerificationBySms;
  }

  isEmail() : boolean {
    return this.model.verificationMean instanceof VerificationByEmail;
  }

  canSubmitAndContinue(): boolean {
    return this.model.isValid();
  }
}
