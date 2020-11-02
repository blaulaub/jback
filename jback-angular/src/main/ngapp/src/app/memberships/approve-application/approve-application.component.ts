import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ClubService } from '../../clubs/club.service';
import { Club } from '../../clubs/club';
import { ClubMembershipApplication } from '../../clubs/club-membership-application';

@Component({
  selector: 'app-approve-application',
  templateUrl: './approve-application.component.html',
  styleUrls: ['./approve-application.component.scss']
})
export class ApproveApplicationComponent implements OnInit {

  application: ClubMembershipApplication;

  constructor(
    private clubService: ClubService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const clubId = this.route.snapshot.paramMap.get('id');
    const applicationId = this.route.snapshot.paramMap.get('applicationId');
    this.clubService.getMembershipApplication(clubId, applicationId).subscribe(it => this.application = it);
  }

}
