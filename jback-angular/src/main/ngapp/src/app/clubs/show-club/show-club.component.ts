import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { ClubService } from '../club.service';
import { Club } from '../club';

@Component({
  selector: 'app-show-club',
  templateUrl: './show-club.component.html',
  styleUrls: ['./show-club.component.scss']
})
export class ShowClubComponent implements OnInit {

  club: Club = null

  constructor(
    private clubService: ClubService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    let id = this.route.snapshot.paramMap.get('id')
    this.clubService.getClub(id).subscribe(it => this.club = it)
  }
}
