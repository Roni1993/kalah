import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KalahPlayAreaComponent } from './kalah-play-area.component';

describe('KalahPlayAreaComponent', () => {
  let component: KalahPlayAreaComponent;
  let fixture: ComponentFixture<KalahPlayAreaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KalahPlayAreaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KalahPlayAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
