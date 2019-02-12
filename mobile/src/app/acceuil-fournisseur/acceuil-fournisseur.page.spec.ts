import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcceuilFournisseurPage } from './acceuil-fournisseur.page';

describe('AcceuilFournisseurPage', () => {
  let component: AcceuilFournisseurPage;
  let fixture: ComponentFixture<AcceuilFournisseurPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcceuilFournisseurPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcceuilFournisseurPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
