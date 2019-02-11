import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import * as moment from 'moment';
import 'moment/locale/pt-br';
import { MenuController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-products',
  templateUrl: './products.page.html',
  styleUrls: ['./products.page.scss'],
})
export class ProductsPage implements OnInit {
  products: any;
  categories: any;
  category: String;

  constructor(private menu: MenuController, private router: Router, private route: ActivatedRoute, private http: HttpClient) {
    moment.locale('FR-fr');
    this.products = [];
    this.categories = [{name: "All"}];
    let page = this;
    this.http.get('localhost:8080/api/product/list').subscribe((response:any) => {
      response.products.forEach(function(productDTO) {
        page.addProduct(productDTO);
      });
      page.http.get('localhost:8080/api/category/list').subscribe((response:any) => {
        response.categories.forEach(function(category) {
          page.addCategory(category);
        });
      }, (err) => {
        console.log(err);
        page.loadPlaceholderCategories();
      });
    }, (err) => {
      console.log(err);
      page.loadPlaceholderProducts();
      page.loadPlaceholderCategories();
    });
  }

  loadPlaceholderProducts() {
    this.addProduct({
      id: 0,
      description: "In mathematics, the dot product or scalar product is an algebraic operation that takes two equal-length sequences of numbers (usually coordinate vectors) and returns a single number.",
      categoryId: 1,
      nameProduct: "Dot Product",
      date: 1550959110725,
      img: "https://www.cmath.fr/1ere/produitscalaire/1images8/dessin5.gif",
      nbOrder: 105,
    });
    this.addProduct({
      id: 1,
      description: "In computing, a computer keyboard is a typewriter-style device which uses an arrangement of buttons or keys to act as mechanical levers or electronic switches. Following the decline of punch cards and paper tape, interaction via teleprinter-style keyboards became the main input method for computers.",
      categoryId: 2,
      nameProduct: "Computer Keyboard",
      date: 1550999110725,
      img: "https://cdn.wccftech.com/wp-content/uploads/2018/11/Wooting-Seasonic-Partnership.jpg",
      nbOrder: 84,
    });
    this.addProduct({
      id: 2,
      description: "A spring is an elastic object that stores mechanical energy. Springs are typically made of spring steel. There are many spring designs. In everyday use, the term often refers to coil springs.",
      categoryId: 3,
      nameProduct: "Spring",
      date: 1953759110725,
      img: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPnpKaQGLvgftLaJhf-P2FSK8xlMpcy4RyslOPo5DtozY6r3AFsA",
      nbOrder: 302,
    });
  }

  loadPlaceholderCategories() {
    this.addCategory({
      id: 1,
      name: "Category 1"
    });
    this.addCategory({
      id: 2,
      name: "Category 2"
    });
    this.addCategory({
      id: 3,
      name: "Category 3"
    });
  }

  addProduct(productDTO) {
    let product = {
      id: productDTO.id,
      description: productDTO.description,
      category: productDTO.categoryId,
      name: productDTO.nameProduct,
      endDate: new Date(productDTO.date),
      image: productDTO.img,
      quantity: productDTO.nbOrder
    };
    this.updateRemainingTime(product);
    this.products.push(product);
  }

  addCategory(category) {
    this.categories.push(category);
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
        let newProduct = this.products[Math.floor(Math.random() * this.products.length)];
        newProduct.id = Math.floor(3 + Math.random() * 1e9);
        newProduct.endDate = new Date();
        let seconds = Math.round(Math.random() * 1000 + 100);
        newProduct.endDate.setSeconds(newProduct.endDate.getSeconds() + seconds);
        this.products.push(newProduct);
      }
      event.target.complete();
      if (this.products.length == 100) {
        event.target.disabled = true;
      }
    }, Math.random() * 1000 + 100);
  }

  goToCategory(categoryId) {
      let url = "/products";
      if(categoryId != null) {
          url += "?category=" + categoryId;
      }
      this.router.navigateByUrl(url);
      this.category = categoryId;
  }

  onCategoryClick(event, categoryId) {
    this.goToCategory(categoryId);
  }

  goToProduct(productId) {
    let url = "/product?id=" + productId;
    console.log(url);
  }

  onProductClick(event, productId) {
    if(productId == null) return;
    this.goToProduct(productId);
  }

  ngOnInit() {
    this.timerTick();
    this.category = this.route.snapshot.queryParamMap.get("category");
  }
}