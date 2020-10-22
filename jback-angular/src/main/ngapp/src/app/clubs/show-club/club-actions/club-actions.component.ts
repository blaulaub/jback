import { Component, OnInit, Input } from '@angular/core';

import {Club } from '../../club';

@Component({
  selector: 'app-club-actions',
  templateUrl: './club-actions.component.html',
  styleUrls: ['./club-actions.component.scss']
})
export class ClubActionsComponent implements OnInit {

  @Input() club: Club;

  constructor() { }

  ngOnInit(): void {
  }
}
