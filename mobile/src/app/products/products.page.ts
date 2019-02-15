import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import * as moment from 'moment';
import 'moment/locale/pt-br';
import { MenuController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';
import {environment} from '../../environments/environment';
import {HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-products',
  templateUrl: './products.page.html',
  styleUrls: ['./products.page.scss'],
})
export class ProductsPage implements OnInit {
  products: any;
  categories: any;
  category: String;
  searchValue: String;

  constructor(private menu: MenuController, private router: Router, private route: ActivatedRoute, private http: HttpClient) {
    moment.locale('FR-fr');
    this.products = [];
    this.categories = [{name: "All"}];
    this.searchValue = '';
    let page = this;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    this.http.get(`${environment.serverURL}/api/product/list`, {headers: headers}).subscribe((response:any) => {
      console.log(response);
      response.forEach(function(productDTO) {
        page.addProduct(productDTO);
      });
      page.http.get(`${environment.serverURL}/api/category/list`, {headers: headers}).subscribe((response:any) => {
        console.log(response);
        response.forEach(function(category) {
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
      categoryId: 2,
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
      categoryId: 2,
      nameProduct: "Spring",
      date: 1953759110725,
      img: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPnpKaQGLvgftLaJhf-P2FSK8xlMpcy4RyslOPo5DtozY6r3AFsA",
      nbOrder: 302,
    });
    this.addProduct({
      id: 3,
      description: "vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum vroum",
      categoryId: 1,
      nameProduct: "Sun Shield",
      date: 2053759110725,
      img: "https://ae01.alicdn.com/kf/HTB1YxCwSVXXXXXWXXXXq6xXFXXXQ/Car-Windshield-Sunshades-Window-Sun-Shield-Visor-Silver-Car-Shade-Sun-Protection-Size-92-cm-142.jpg_640x640.jpg",
      nbOrder: 30200,
    });
  }

  loadPlaceholderCategories() {
    this.addCategory({
      categoryId: 1,
      name: "Automobile"
    });
    this.addCategory({
      categoryId: 2,
      name: "Divers"
    });
  }

  addProduct(productDTO) {
    const product = {
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

  addCategory(categoryDTO) {
    const category = {
      id: categoryDTO.categoryId,
      name: categoryDTO.name
    }
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
        let newProduct = JSON.parse(JSON.stringify(this.products[Math.floor(Math.random() * this.products.length)]));
        newProduct.id = Math.floor(4 + Math.random() * 1e9);
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
    let url = "/product-details?id=" + productId;
    this.router.navigateByUrl(url);
  }

  onProductClick(event, productId) {
    if(productId == null) return;
    this.goToProduct(productId);
  }

  onSearchBarChange() {
    console.log(this.searchValue);
  }

  ngOnInit() {
    this.timerTick();
    this.category = this.route.snapshot.queryParamMap.get("category");
  }
}
