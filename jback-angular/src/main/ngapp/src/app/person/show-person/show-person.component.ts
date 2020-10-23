import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { PersonService } from '../person.service';
import { Person } from '../person';

@Component({
  selector: 'app-show-person',
  templateUrl: './show-person.component.html',
  styleUrls: ['./show-person.component.scss']
})
export class ShowPersonComponent implements OnInit {

  person: Person = null;

  constructor(
    private personService: PersonService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.personService.getPerson(id).subscribe(it => this.person = it);
  }

}
