import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.page.html',
  styleUrls: ['./product-details.page.scss'],
})
export class ProductDetailsPage implements OnInit {
  productId: Number;
  product: any;
  selectedOptionId: Number;

  constructor(private route: ActivatedRoute) {
    let page = this;
    this.selectedOptionId = 0;
    this.route.queryParams.subscribe(params => {
      page.productId = params["id"];
      page.product = {
        id: page.productId,
        name: "Sun Shield",
        description: "vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum",
        image: "https://ae01.alicdn.com/kf/HTB1YxCwSVXXXXXWXXXXq6xXFXXXQ/Car-Windshield-Sunshades-Window-Sun-Shield-Visor-Silver-Car-Shade-Sun-Protection-Size-92-cm-142.jpg_640x640.jpg",
        options: [{
          id: 0,
          name: "92 cm"
        }, {
          id: 1,
          name: "104 cm"
        }, {
          id: 2,
          name: "116 cm"
        }]
      }
    });
  }

  ngOnInit() {

  }
}
