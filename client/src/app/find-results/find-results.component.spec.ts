import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FindResultsComponent } from './find-results.component';

describe('FindResultsComponent', () => {
  let component: FindResultsComponent;
  let fixture: ComponentFixture<FindResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FindResultsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FindResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
