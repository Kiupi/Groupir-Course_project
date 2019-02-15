import {Component, Input, OnInit} from '@angular/core';
import {HistoryPurchase} from "../../../interface/history-purchase";
import {UserPage} from '../../user.page';

@Component({
  selector: 'app-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.scss']
})
export class PurchaseHistoryComponent implements OnInit {
  historyPurchase: Array<HistoryPurchase>;
  constructor(private readonly userPage: UserPage) { }

  ngOnInit() {
    this.historyPurchase=this.userPage.historyPurchase;
    console.log(this.historyPurchase)
    this.historyPurchase.forEach((purchase)=>{
        purchase.dateOrder=new Date(purchase.dateOrder);
    })

    console.log(this.historyPurchase);
  }

}
