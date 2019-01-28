import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotPagePage } from './forgot-page.page';

describe('ForgotPagePage', () => {
  let component: ForgotPagePage;
  let fixture: ComponentFixture<ForgotPagePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgotPagePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotPagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
