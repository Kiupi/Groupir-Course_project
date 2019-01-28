import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LogginPage } from './loggin.page';

describe('LogginPage', () => {
  let component: LogginPage;
  let fixture: ComponentFixture<LogginPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogginPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogginPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
