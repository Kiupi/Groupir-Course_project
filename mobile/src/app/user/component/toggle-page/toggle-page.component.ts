import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-toggle-page',
  templateUrl: './toggle-page.component.html',
  styleUrls: ['./toggle-page.component.scss']
})
export class TogglePageComponent implements OnInit {

  constructor() { }

  @Output() onToggle = new EventEmitter<string>();

  goDetailPage() {
    this.onToggle.emit('detail');
  }
  goAddressListPage() {
    this.onToggle.emit('address-list');
  }
  goPaymentListPage() {
    this.onToggle.emit('payment-list');
  }
  goPurchaseHistoryPage() {
    this.onToggle.emit('purchase-history');
  }

  ngOnInit() {
  }
}
