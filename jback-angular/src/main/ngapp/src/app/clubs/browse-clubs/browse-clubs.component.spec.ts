import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseClubsComponent } from './browse-clubs.component';

describe('BrowseClubsComponent', () => {
  let component: BrowseClubsComponent;
  let fixture: ComponentFixture<BrowseClubsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrowseClubsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BrowseClubsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
