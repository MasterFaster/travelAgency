import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CashStatsComponent } from './cash-stats.component';

describe('CashStatsComponent', () => {
  let component: CashStatsComponent;
  let fixture: ComponentFixture<CashStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CashStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CashStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
