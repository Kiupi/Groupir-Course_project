import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdressListComponent } from './adress-list.component';

describe('AdressListComponent', () => {
  let component: AdressListComponent;
  let fixture: ComponentFixture<AdressListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdressListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdressListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
