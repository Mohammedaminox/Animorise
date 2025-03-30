import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RendezvousVeterinereComponent } from './rendezvous-veterinere.component';

describe('RendezvousVeterinereComponent', () => {
  let component: RendezvousVeterinereComponent;
  let fixture: ComponentFixture<RendezvousVeterinereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RendezvousVeterinereComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RendezvousVeterinereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
