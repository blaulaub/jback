import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormGroup,
  Validators,
  FormBuilder,
  AbstractControl,
  ValidatorFn,
  ValidationErrors
} from '@angular/forms';

import { RegistrationService } from '../registration.service';

import { InitialRegistrationData } from '../initial-registration-data';
import { PendingRegistrationInfo } from '../pending-registration-info';
import { VerificationMean } from '../../verification-means/verification-mean';
import { VerificationByEmail } from '../../verification-means/verification-by-email';
import { VerificationBySms } from '../../verification-means/verification-by-sms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  readonly verificationMeans = [
    new VerificationByEmail(),
    new VerificationBySms()
  ];

  registrationForm: FormGroup;

  constructor(
    private registrationService: RegistrationService,
    private router: Router,
    private fb: FormBuilder
  ) {

    const verificationMean = this.fb.control(null, [ RegisterComponent.validVerificationMean ]);

    this.registrationForm = this.fb.group({
      firstName: this.fb.control(null, [ Validators.required ]),
      lastName: this.fb.control(null, [ Validators.required ]),
      verificationMean: verificationMean,
      byEmail: this.fb.group({
        emailAddress: this.fb.control(null, [ this.validEmailIfRequired(verificationMean) ])
      }),
      bySms: this.fb.group({
        phoneNumber: this.fb.control(null, [ this.validPhoneNumberIfRequired(verificationMean) ])
      })
    });
  }

  ngOnInit(): void {
  }

  recheck(): void {
    (this.registrationForm.controls.byEmail as FormGroup).controls.emailAddress.updateValueAndValidity();
    (this.registrationForm.controls.bySms as FormGroup).controls.phoneNumber.updateValueAndValidity();
  }

  static validVerificationMean(c: AbstractControl): ValidationErrors {

    if (c.value === null) {
      return { verificationMean: "must be selected" };
    } else {
      return {};
    }
  }

  validEmailIfRequired(mean: AbstractControl): ValidatorFn {

    return (control: AbstractControl): {[key: string]: any} | null => {
      if (mean.value?.type === 'email') {
        return Validators.required(control);
      } else {
        return RegisterComponent.validVerificationMean(mean);
      }
    }
  }

  validPhoneNumberIfRequired(mean: AbstractControl): ValidatorFn {

    return (control: AbstractControl): {[key: string]: any} | null => {
      if (mean.value?.type === 'sms') {
        return Validators.required(control);
      } else {
        return RegisterComponent.validVerificationMean(mean);
      }
    }
  }

  private toVerificationMean(): VerificationMean {
    if (this.registrationForm.controls.verificationMean?.value.type === 'email') {
      let result = new VerificationByEmail();
      result.emailAddress = (this.registrationForm.controls.byEmail as FormGroup).controls.emailAddress.value;
      return result;
    }
    if (this.registrationForm.controls.verificationMean?.value.type === 'sms') {
      let result = new VerificationBySms();
      result.phoneNumber = (this.registrationForm.controls.bySms as FormGroup).controls.phoneNumber.value;
      return result;      
    }
    console.log('seems to be missing');
    return null;
  }

  submit(): void {
    this.registrationService.postInitialRegistrationData({
      firstName: this.registrationForm.controls.firstName.value,
      lastName: this.registrationForm.controls.lastName.value,
      verificationMean: this.toVerificationMean()
      }).subscribe(result => this.navigateTo(result));
  }

  private navigateTo(info: PendingRegistrationInfo): void {
    this.router.navigate(['complete', info.pendingRegistrationId]);
  }
}
