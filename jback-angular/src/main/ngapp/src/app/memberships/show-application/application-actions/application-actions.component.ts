import { Component, OnInit, Input } from '@angular/core';

import { Club } from '../../../clubs/club';
import { ClubMembershipApplication } from '../../../clubs/club-membership-application';

@Component({
  selector: 'app-application-actions',
  templateUrl: './application-actions.component.html',
  styleUrls: ['./application-actions.component.scss']
})
export class ApplicationActionsComponent implements OnInit {

  @Input() application: ClubMembershipApplication;

  get club(): Club {
    return this.application.club;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
