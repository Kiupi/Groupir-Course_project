import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.page.html',
  styleUrls: ['./product-details.page.scss'],
})
export class ProductDetailsPage implements OnInit {
  productId: Number;

  constructor(private route: ActivatedRoute) {
    let page = this;
    this.route.queryParams.subscribe(params => {
      page.productId = params["id"];
    });
  }

  ngOnInit() {
  }

}
