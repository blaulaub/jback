import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { RegistrationService } from '../registration.service';

import { InitialRegistrationData } from '../initial-registration-data';
import { PendingRegistrationInfo } from '../pending-registration-info';
import { VerificationByEmail } from '../../verification-means/verification-by-email';
import { VerificationBySms } from '../../verification-means/verification-by-sms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  readonly verificationMeans = [ new VerificationByEmail(), new VerificationBySms() ];

  model = new InitialRegistrationData();

  constructor(
    private registrationService: RegistrationService,
    private router: Router
  ) { }

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
      .subscribe(result => this.navigateTo(result));
  }

  private navigateTo(info: PendingRegistrationInfo) {
    this.router.navigate(["complete", info.pendingRegistrationId]);
  }
}
