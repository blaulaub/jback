import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMeComponent } from './create-me.component';

describe('CreateMeComponent', () => {
  let component: CreateMeComponent;
  let fixture: ComponentFixture<CreateMeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
