import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import 'moment/locale/pt-br';

@Component({
  selector: 'app-products',
  templateUrl: './products.page.html',
  styleUrls: ['./products.page.scss'],
})
export class ProductsPage implements OnInit {
  products: any;

  constructor() {
    moment.locale('FR-fr');
    let page = this;
    this.products = [
      {quantity: 105, name: "Dot Product", image: "https://www.cmath.fr/1ere/produitscalaire/1images8/dessin5.gif", description: "In mathematics, the dot product or scalar product is an algebraic operation that takes two equal-length sequences of numbers (usually coordinate vectors) and returns a single number."},
      {quantity: 84, name: "Computer Keyboard", image: "https://cdn.wccftech.com/wp-content/uploads/2018/11/Wooting-Seasonic-Partnership.jpg", description: "In computing, a computer keyboard is a typewriter-style device which uses an arrangement of buttons or keys to act as mechanical levers or electronic switches. Following the decline of punch cards and paper tape, interaction via teleprinter-style keyboards became the main input method for computers."},
      {quantity: 302, name: "Spring", image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPnpKaQGLvgftLaJhf-P2FSK8xlMpcy4RyslOPo5DtozY6r3AFsA", description: "A spring is an elastic object that stores mechanical energy. Springs are typically made of spring steel. There are many spring designs. In everyday use, the term often refers to coil springs."},
    ];
    this.products.forEach(function(product) {
      product.endDate = new Date();
      let seconds = Math.round(Math.random() * 10000 + 100);
      product.endDate.setSeconds(product.endDate.getSeconds() + seconds);
      page.updateRemainingTime(product);
    });

  }

  updateRemainingTime(product) {
    product.remainingTime = moment(product.endDate).from(moment());
  }

  timerTick() {
    setInterval(() => {
      for(let i = 0; i < this.products.length; i++) {
        this.updateRemainingTime(this.products[i]);
      }
    }, 1000);
  }

  loadData(event) {
    setTimeout(() => {
      for(let i = 0; i < 3; i++) {
        this.products.push(this.products[i])
      }
      event.target.complete();
      if (this.products.length == 100) {
        event.target.disabled = true;
      }
    }, Math.random() * 1000 + 100);
  }

  ngOnInit() {
    this.timerTick();
  }

}