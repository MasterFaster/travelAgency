import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationAddFlightComponent } from './reservation-add-flight.component';

describe('ReservationAddFlightComponent', () => {
  let component: ReservationAddFlightComponent;
  let fixture: ComponentFixture<ReservationAddFlightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationAddFlightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationAddFlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
