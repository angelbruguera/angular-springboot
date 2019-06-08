import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaticSiteComponent } from './static-site.component';

describe('StaticSiteComponent', () => {
  let component: StaticSiteComponent;
  let fixture: ComponentFixture<StaticSiteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaticSiteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaticSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
