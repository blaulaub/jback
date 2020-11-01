import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { ClubService } from '../../clubs/club.service';
import { ClubMembershipApplication } from '../../clubs/club-membership-application';

@Component({
  selector: 'app-show-applications',
  templateUrl: './show-applications.component.html',
  styleUrls: ['./show-applications.component.scss']
})
export class ShowApplicationsComponent implements OnInit {

  private id: string;

  applications: ClubMembershipApplication[];

  constructor(
    private clubService: ClubService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.clubService.getMembershipApplications(this.id).subscribe(it => this.applications = it);
  }
}
