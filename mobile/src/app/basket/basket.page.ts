import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.page.html',
  styleUrls: ['./basket.page.scss'],
})
export class BasketPage implements OnInit {

  public basket: Array<any>;
  public prices: Array<number>;
  public priceSum = 0;

  constructor(private router: Router) { }

  ngOnInit() {
    this.basket = this.loadBasket();
    this.getPrices();
  }

  loadBasket() {
    const array = JSON.parse(localStorage.getItem('basket'));
    return (array == null) ? [] : array;
  }

  deleteProduct(product) {
    this.basket = this.arrayRemove(this.basket, product);
    this.persistBasket(this.basket);
    console.log('The product has been deleted');
  }

  persistBasket(basket) {
    return localStorage.setItem('basket', JSON.stringify(basket));
  }

  arrayRemove(arr, value) {
    return arr.filter(function(ele) {
      return ele !== value;
    });
  }

  getPrices() {
    let test = 0;
    this.prices = [];
    for (const product in this.basket) {
      this.prices.push(test);
      test++;
    }
    this.countPriceSum();
  }

  countPriceSum() {
    let sum = 0;
    for (const price in this.prices) {
      sum++;
    }
    this.priceSum = sum;
  }

  goToProducts() {
    const url = '/products';
    this.router.navigateByUrl(url);
  }

  goToProfile() {

  }

  disconnect() {

  }
}
