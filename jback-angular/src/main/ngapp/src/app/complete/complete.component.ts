import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { RegistrationService } from '../registration/registration.service';

import { VerificationCode } from '../registration/verification-code';

@Component({
  selector: 'app-complete',
  templateUrl: './complete.component.html',
  styleUrls: ['./complete.component.scss']
})
export class CompleteComponent implements OnInit {

  private id: string;

  model = new VerificationCode();

  constructor(
    private registrationService: RegistrationService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
  }

  canComplete(): boolean {
    return this.model.isValid();
  }

  complete() {
    this.registrationService.putVerificationCode(this.id, this.model)
      .subscribe(result => console.log(result));
  }
}
