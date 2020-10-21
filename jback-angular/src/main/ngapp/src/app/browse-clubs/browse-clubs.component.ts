import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';

import { ClubService } from '../clubs/club.service';
import { Club } from '../clubs/club';

@Component({
  selector: 'app-browse-clubs',
  templateUrl: './browse-clubs.component.html',
  styleUrls: ['./browse-clubs.component.scss']
})
export class BrowseClubsComponent implements OnInit {

  searchTerm: string = null

  matchingClubs: Club[] = null

  private searchTerms = new Subject<string>();

  constructor(
    private clubService: ClubService
  ) { }

  ngOnInit(): void {

    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.clubService.searchClubs(term))
    ).subscribe(clubs => this.matchingClubs = clubs)
  }

  matchingClubsCount(): Number {

    if (this.matchingClubs == null) {
      return 0;
    }

    return this.matchingClubs.length;
  }

  updateSearch(newTerm) {
    this.searchTerms.next(newTerm)
  }

}
