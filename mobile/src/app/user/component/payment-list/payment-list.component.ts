import {Component, Input, OnInit} from '@angular/core';
import {PaymentList} from "../../../interface/payment-list";

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {
  @Input() paymentList: PaymentList;
  constructor() { }

  ngOnInit() {
  }

}
