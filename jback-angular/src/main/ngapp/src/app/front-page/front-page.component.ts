import { Component, OnInit } from '@angular/core';

import { SessionService } from '../session/session.service';

import { Perspective } from '../session/perspective';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.scss']
})
export class FrontPageComponent implements OnInit {

  perspective: keyof typeof Perspective;

  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
    this.sessionService.getSessionInfo()
    .subscribe(result => {
      this.perspective = result.perspective;
    })
  }

  userIsGuest(): boolean {
    return Perspective[this.perspective] ==  Perspective.GUEST;
  }

  userIsEnrolling(): boolean {
    return Perspective[this.perspective] ==  Perspective.ENROLLING;
  }

  userIsMember(): boolean {
    return Perspective[this.perspective] ==  Perspective.MEMBER;
  }
}
