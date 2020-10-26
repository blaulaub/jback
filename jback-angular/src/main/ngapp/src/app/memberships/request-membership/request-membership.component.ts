import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {
  FormGroup,
  Validators,
  FormBuilder,
  AbstractControl
} from '@angular/forms';

import { SessionService } from '../../session/session.service';
import { ClubService } from '../../clubs/club.service';
import { PersonService } from '../../person/person.service'
import { Club } from '../../clubs/club';
import { Person } from '../../person/person';


@Component({
  selector: 'app-request-membership',
  templateUrl: './request-membership.component.html',
  styleUrls: ['./request-membership.component.scss']
})
export class RequestMembershipComponent implements OnInit {

  membershipForm: FormGroup;

  club: Club = null;
  persons: Person[] = null;

  constructor(
    private sessionService: SessionService,
    private clubService: ClubService,
    private personService: PersonService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {

    this.membershipForm = this.fb.group({
      person: this.fb.control(null, [ Validators.required ])
    });

  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.clubService.getClub(id).subscribe(it => this.club = it);
    this.personService.getPersonForCurrentPrincipal().subscribe(it => this.persons = it);
  }

  displayName(person: Person): string {
    return [person.firstName, person.lastName].join(' ');
  }

  submit(): void {
    console.error('Not implemented');
  }

}
