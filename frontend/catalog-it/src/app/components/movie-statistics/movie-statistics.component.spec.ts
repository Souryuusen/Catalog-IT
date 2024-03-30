import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieStatisticsComponent } from './movie-statistics.component';

describe('MovieStatisticsComponent', () => {
  let component: MovieStatisticsComponent;
  let fixture: ComponentFixture<MovieStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieStatisticsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MovieStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
