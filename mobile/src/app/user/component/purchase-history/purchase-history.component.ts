import {Component, Input, OnInit} from '@angular/core';
import {HistoryPurchase} from "../../../interface/history-purchase";

@Component({
  selector: 'app-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.scss']
})
export class PurchaseHistoryComponent implements OnInit {
  @Input() historyPurchase: HistoryPurchase;
  constructor() { }

  ngOnInit() {
    console.log(this.historyPurchase);
  }

}