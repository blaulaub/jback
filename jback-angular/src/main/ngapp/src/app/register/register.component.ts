import { Component, OnInit } from '@angular/core';

import { InitialRegistrationData } from '../initial-registration-data';
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
  verificationMethod: string;
  emailAddress: string;
  phoneNumber: string;

  constructor() { }

  ngOnInit(): void {
  }

  private notNullOrEmpty(str: string): boolean {
    return typeof str === "string" && str !== "";
  }

  private verificationMethodValid() : boolean {
    return this.notNullOrEmpty(this.verificationMethod) && (
        this.verificationMethod === "SMS" && this.notNullOrEmpty(this.phoneNumber) ||
        this.verificationMethod === "Email" && this.notNullOrEmpty(this.emailAddress)
      );
  }

  canSubmitAndContinue(): boolean {
    return this.notNullOrEmpty(this.data.firstName) &&
      this.notNullOrEmpty(this.data.lastName) &&
      this.verificationMethodValid();
  }

}
