import { TestBed } from '@angular/core/testing';

import { EventingService } from './eventing.service';

describe('EventingService', () => {
  let service: EventingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
