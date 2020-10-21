import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SessionService } from '../session/session.service';

import { Perspective } from '../session/perspective';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.scss']
})
export class FrontPageComponent implements OnInit {

  perspective: keyof typeof Perspective;

  userId: string | null = null;

  constructor(
    private sessionService: SessionService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.updatePerspective();
  }

  private updatePerspective() {
    this.sessionService.getSessionInfo()
      .subscribe(result => {
        this.perspective = result.perspective;
        this.userId = result.userId;
      });
  }

  logout() {
    this.sessionService.postLogout()
      .subscribe(() => this.updatePerspective())
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
