import { Component, OnInit } from '@angular/core';
import {User} from "../../../interface/user.interface";
import {HistoryPurchase} from "../../../interface/history-purchase";

@Component({
  selector: 'app-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.scss']
})
export class PurchaseHistoryComponent implements OnInit {
  historyPurchase: HistoryPurchase;
  constructor() { }

  ngOnInit() {
    this.historyPurchase = JSON.parse(localStorage.getItem("historyPurchase"));
  }

}
/*
* "trackingNumber": null,
        "dispatchmentDate": null,
        "quantity": 14,
        "optionId": 7,
        "optionName": "Men",
        "image": null,
        "productId": 4,
        "productName": "String",
        "price": 15*/