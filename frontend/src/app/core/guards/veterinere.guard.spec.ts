import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { veterinereGuard } from './veterinere.guard';

describe('veterinereGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => veterinereGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
