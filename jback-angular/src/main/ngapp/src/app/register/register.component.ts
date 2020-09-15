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
  method: string;
  emailAddress: string;
  phoneNumber: string;

  constructor() { }

  ngOnInit(): void {
  }

  canSubmitAndContinue(): boolean {
    return true;
  }

}
