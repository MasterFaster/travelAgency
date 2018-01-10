import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationFlightMainDetailsComponent } from './reservation-flight-main-details.component';

describe('ReservationFlightMainDetailsComponent', () => {
  let component: ReservationFlightMainDetailsComponent;
  let fixture: ComponentFixture<ReservationFlightMainDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationFlightMainDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationFlightMainDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
