import { Component, OnInit } from '@angular/core';
import {PaymentList} from "../../../interface/payment-list";

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {
  paymentList: PaymentList;
  constructor() { }

  ngOnInit() {
    this.paymentList = JSON.parse(localStorage.getItem("paymentList"));
  }

}
