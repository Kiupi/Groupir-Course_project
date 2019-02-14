import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth.service";
import {PaymentList} from "../interface/payment-list";
import {User} from "../interface/user.interface";

@Component({
  selector: 'app-user',
  templateUrl: './user.page.html',
  styleUrls: ['./user.page.scss'],
})
export class UserPage implements OnInit {
  public pageNumber = 'toggle-page';
  public paymentList: PaymentList;
  private user: User;

  constructor(private httpClient: HttpClient, private readonly authService: AuthService) {
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem("user"));
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
    console.log(this.user);
    const headers = this.authService.setHeadersToken();
    console.log(headers);
    this.httpClient.get(`${environment.serverURL}/api/user/${this.user.userId}/payment-method/list`, {
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
    console.log(this.user);
    const headers = this.authService.setHeadersToken();
    console.log(headers);
    // url : `${environment.serverURL}/api/user/${user.id}/adress
    // on rajoute values en deuxieme param pour un post
    this.httpClient.get(`${environment.serverURL}/api/user/history_purchase/${this.user.userId}`, {
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
