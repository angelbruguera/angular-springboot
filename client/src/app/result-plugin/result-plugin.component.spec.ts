import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultPluginComponent } from './result-plugin.component';

describe('ResultPluginComponent', () => {
  let component: ResultPluginComponent;
  let fixture: ComponentFixture<ResultPluginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultPluginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultPluginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
