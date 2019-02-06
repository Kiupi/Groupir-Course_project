import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import * as moment from 'moment';
import 'moment/locale/pt-br';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-products',
  templateUrl: './products.page.html',
  styleUrls: ['./products.page.scss'],
})
export class ProductsPage implements OnInit {
  products: any;
  categories: any;
  category: String;

  constructor(private menu: MenuController, private router: Router, private route: ActivatedRoute) {
    moment.locale('FR-fr');
    let page = this;
    this.products = [
      {quantity: 105, category: 1, name: "Dot Product", image: "https://www.cmath.fr/1ere/produitscalaire/1images8/dessin5.gif", description: "In mathematics, the dot product or scalar product is an algebraic operation that takes two equal-length sequences of numbers (usually coordinate vectors) and returns a single number."},
      {quantity: 84, category: 2, name: "Computer Keyboard", image: "https://cdn.wccftech.com/wp-content/uploads/2018/11/Wooting-Seasonic-Partnership.jpg", description: "In computing, a computer keyboard is a typewriter-style device which uses an arrangement of buttons or keys to act as mechanical levers or electronic switches. Following the decline of punch cards and paper tape, interaction via teleprinter-style keyboards became the main input method for computers."},
      {quantity: 302, category: 3, name: "Spring", image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPnpKaQGLvgftLaJhf-P2FSK8xlMpcy4RyslOPo5DtozY6r3AFsA", description: "A spring is an elastic object that stores mechanical energy. Springs are typically made of spring steel. There are many spring designs. In everyday use, the term often refers to coil springs."},
    ];
    this.products.forEach(function(product) {
      product.endDate = new Date();
      let seconds = Math.round(Math.random() * 1000 + 100);
      product.endDate.setSeconds(product.endDate.getSeconds() + seconds);
      page.updateRemainingTime(product);
    });
    this.categories = [{name: "All"}, {name: "Categorie 1", id: 1}, {name: "Categorie 2", id: 2}, {name: "Categorie 3", id: 3}];
  }

  openCategoriesMenu() {
    this.menu.enable(true, 'categories');
    this.menu.open('categories');
  }

  updateRemainingTime(product) {
    product.remainingTime = moment(product.endDate).fromNow();
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
        this.products.push(this.products[Math.floor(Math.random() * this.products.length)]);
      }
      event.target.complete();
      if (this.products.length == 100) {
        event.target.disabled = true;
      }
    }, Math.random() * 1000 + 100);
  }

  goToCategory(category) {
      let url = "/products";
      if(category != null) {
          url += "?category=" + category;
      }
      this.router.navigateByUrl(url);
      this.category = category;
  }

  onCategoryClick(event, categoryId) {
    this.menu.close('categories');
    this.goToCategory(categoryId);
  }

  ngOnInit() {
    this.timerTick();
    this.category = this.route.snapshot.queryParamMap.get("category");
  }
}