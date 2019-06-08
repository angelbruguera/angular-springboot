import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PluginQuestionComponent } from './plugin-question.component';

describe('PluginQuestionComponent', () => {
  let component: PluginQuestionComponent;
  let fixture: ComponentFixture<PluginQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PluginQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PluginQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
