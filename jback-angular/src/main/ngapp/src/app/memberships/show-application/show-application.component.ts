import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { ClubService } from '../../clubs/club.service';
import { ClubMembershipApplication } from '../../clubs/club-membership-application';

@Component({
  selector: 'app-show-application',
  templateUrl: './show-application.component.html',
  styleUrls: ['./show-application.component.scss']
})
export class ShowApplicationComponent implements OnInit {

  private clubId: string;
  private applicationId: string;

  application: ClubMembershipApplication;

  constructor(
    private clubService: ClubService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.clubId = this.route.snapshot.paramMap.get('id');
    this.applicationId = this.route.snapshot.paramMap.get('applicationId');
    this.clubService.getMembershipApplication(this.clubId, this.applicationId).subscribe(it => this.application = it);
  }
}
