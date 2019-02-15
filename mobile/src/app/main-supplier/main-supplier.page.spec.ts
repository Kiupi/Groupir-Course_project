import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSupplierPage } from './main-supplier.page';

describe('MainSupplierPage', () => {
  let component: MainSupplierPage;
  let fixture: ComponentFixture<MainSupplierPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainSupplierPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainSupplierPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
