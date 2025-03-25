import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeciesFormComponent } from './species-form.component';

describe('SpeciesFormComponent', () => {
  let component: SpeciesFormComponent;
  let fixture: ComponentFixture<SpeciesFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpeciesFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpeciesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
