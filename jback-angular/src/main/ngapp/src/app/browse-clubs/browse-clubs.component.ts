import { Component, OnInit } from '@angular/core';

import { ClubService } from '../clubs/club.service';
import { Club } from '../clubs/club';

@Component({
  selector: 'app-browse-clubs',
  templateUrl: './browse-clubs.component.html',
  styleUrls: ['./browse-clubs.component.scss']
})
export class BrowseClubsComponent implements OnInit {

  searchTerm: String = null

  matchingClubs: Club[] = null

  constructor(
    private clubService: ClubService
  ) { }

  ngOnInit(): void {
  }

  matchingClubsCount(): Number {

    if (this.matchingClubs == null) {
      return 0;
    }

    return this.matchingClubs.length;
  }

  updateSearch(newTerm) {
    this.clubService.searchClubs(newTerm)
      .subscribe(it => this.matchingClubs = it)
  }

}
