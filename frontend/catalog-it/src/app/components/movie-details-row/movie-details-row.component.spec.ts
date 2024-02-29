import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieDetailsRowComponent } from './movie-details-row.component';

describe('MovieDetailsRowComponent', () => {
  let component: MovieDetailsRowComponent;
  let fixture: ComponentFixture<MovieDetailsRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieDetailsRowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MovieDetailsRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
