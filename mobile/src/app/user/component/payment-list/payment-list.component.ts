import {Component, Input, OnInit} from '@angular/core';
import {PaymentList} from "../../../interface/payment-list";
import {UserPage} from '../../user.page';

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {
  paymentList: PaymentList;
  constructor(private readonly userPage: UserPage) { }

  ngOnInit() {
     this.paymentList=this.userPage.paymentList;
      console.log(this.userPage.paymentList)
  }

}
