import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.page.html',
  styleUrls: ['./basket.page.scss'],
})
export class BasketPage implements OnInit {

  public basket: Array<any>;

  constructor() { }

  ngOnInit() {
    this.basket = this.loadBasket();
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

  goToProducts() {
  }
}
