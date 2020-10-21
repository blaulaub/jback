import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowClubComponent } from './show-club.component';

describe('ShowClubComponent', () => {
  let component: ShowClubComponent;
  let fixture: ComponentFixture<ShowClubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowClubComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowClubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
