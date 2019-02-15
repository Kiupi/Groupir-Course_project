import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.page.html',
  styleUrls: ['./basket.page.scss'],
})
export class BasketPage implements OnInit {

  public basket: Array<any>;
  public prices: Array<number>;
  public priceSum = 0;
  // public productMap = [{name: '', option: '', price: ''}];

  constructor(private router: Router, private http: HttpClient) { }

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
    this.basket = this.loadBasket();
    this.getPrices();
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
    // let test = 0;
    this.prices = [];
    this.prices = this.basket.map(productMap => productMap.price);

    /*for (const product in this.basket) {


      const productPrices = product.map(productMap => productMap.price);
      this.prices.push(productPrices);
      test++;
    }*/
    this.countPriceSum();
  }

  countPriceSum() {
    this.priceSum = 0;
    for (let i = 0; i < this.prices.length; i++) {
      this.priceSum += +this.prices[i];
    }
  }

  goToProducts() {
    const url = '/products';
    this.router.navigateByUrl(url);
  }

  goToProfile() {

  }

  disconnect() {

  }

  confirm() {
    const url = '/basket-confirm';
    this.router.navigateByUrl(url);
  }
}
