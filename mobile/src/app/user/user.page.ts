import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth.service";
import {PaymentList} from "../interface/payment-list";

@Component({
  selector: 'app-user',
  templateUrl: './user.page.html',
  styleUrls: ['./user.page.scss'],
})
export class UserPage implements OnInit {
  public pageNumber = 'toggle-page';
  public paymentList: PaymentList;
  private userId: number;
  constructor(private httpClient: HttpClient, private readonly authService: AuthService) { }

  ngOnInit() {
    this.userId = JSON.parse(localStorage.getItem("user"))[0].id;
    this.requestPaymentList();
    this.requestHistoryPurchase();
  }

  onToggle(value) {
    this.pageNumber = value;
  }

  goTogglePage() {
    this.pageNumber = 'toggle-page';
  }

  requestPaymentList() {
    const headers = this.authService.setHeadersToken();
    console.log(headers);
    this.httpClient.get(`${environment.serverURL}/api/user/${this.userId}/payment-method/list`, {
      'headers': headers,
      'responseType': 'text'
    })
        .subscribe((data) => {
          console.log(data);
          this.paymentList = JSON.parse(data);
          console.log(this.paymentList);
        });
  }

  requestHistoryPurchase() {
    const headers = this.authService.setHeadersToken();
    console.log(headers);
    this.userId = JSON.parse(localStorage.getItem("user"))[0].id;
    // url : `${environment.serverURL}/api/user/${user.id}/adress
    // on rajoute values en deuxieme param pour un post
    this.httpClient.get(`${environment.serverURL}/api/user/history_purchase/${this.userId}`, {
      'headers': headers,
      'responseType': 'text'
    })
        .subscribe((data) => {
          console.log(data);
          this.paymentList = JSON.parse(data);
          console.log(this.paymentList);
        });
  }
}
