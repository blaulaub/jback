import { Component, OnInit, Input } from '@angular/core';

import { Person } from '../../person';

@Component({
  selector: 'app-person-actions',
  templateUrl: './person-actions.component.html',
  styleUrls: ['./person-actions.component.scss']
})
export class PersonActionsComponent implements OnInit {

  @Input() person: Person;

  constructor() { }

  ngOnInit(): void {
  }

}
