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

  data = new InitialRegistrationData();
  verificationMethod: VerificationMean;
  emailAddress: string;
  phoneNumber: string;

  constructor() { }

  ngOnInit(): void {
  }

  isSms() : boolean {
    return this.verificationMethod instanceof VerificationBySms;
  }

  isEmail() : boolean {
    return this.verificationMethod instanceof VerificationByEmail;
  }

  private notNullOrEmpty(str: string): boolean {
    return typeof str === "string" && str !== "";
  }

  canSubmitAndContinue(): boolean {
    return this.notNullOrEmpty(this.data.firstName) &&
      this.notNullOrEmpty(this.data.lastName) &&
      this.verificationMethod.isValid();
  }

}
