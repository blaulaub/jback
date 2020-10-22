import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { SessionService } from '../../session/session.service';
import { ClubService } from '../../clubs/club.service';
import { Club } from '../../clubs/club';


@Component({
  selector: 'app-request-membership',
  templateUrl: './request-membership.component.html',
  styleUrls: ['./request-membership.component.scss']
})
export class RequestMembershipComponent implements OnInit {

  club: Club = null;

  constructor(
    private sessionService: SessionService,
    private clubService: ClubService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    let id = this.route.snapshot.paramMap.get('id')
    this.clubService.getClub(id).subscribe(it => this.club = it)
  }

}
