import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowPersonComponent } from './show-person.component';

describe('ShowPersonComponent', () => {
  let component: ShowPersonComponent;
  let fixture: ComponentFixture<ShowPersonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowPersonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowPersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
