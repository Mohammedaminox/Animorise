import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalsVeterinereComponent } from './animals-veterinere.component';

describe('AnimalsVeterinereComponent', () => {
  let component: AnimalsVeterinereComponent;
  let fixture: ComponentFixture<AnimalsVeterinereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimalsVeterinereComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnimalsVeterinereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
