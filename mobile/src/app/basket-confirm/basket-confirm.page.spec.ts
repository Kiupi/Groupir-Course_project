import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BasketConfirmPage } from './basket-confirm.page';

describe('BasketConfirmPage', () => {
  let component: BasketConfirmPage;
  let fixture: ComponentFixture<BasketConfirmPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BasketConfirmPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BasketConfirmPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
