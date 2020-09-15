import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  readonly methodOptions = [ "Email", "SMS" ];

  firstName: string;
  lastName: string;
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
    return this.notNullOrEmpty(this.firstName) &&
      this.notNullOrEmpty(this.lastName) &&
      this.verificationMethodValid();
  }

}
