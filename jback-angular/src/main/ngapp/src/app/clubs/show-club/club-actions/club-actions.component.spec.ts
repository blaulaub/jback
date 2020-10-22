import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubActionsComponent } from './club-actions.component';

describe('ClubActionsComponent', () => {
  let component: ClubActionsComponent;
  let fixture: ComponentFixture<ClubActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClubActionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClubActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
