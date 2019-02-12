import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-adress-list',
  templateUrl: './adress-list.component.html',
  styleUrls: ['./adress-list.component.scss']
})
export class AdressListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}

/*
*
* import {Component, OnInit} from "@angular/core";
import {AvailablePostsService} from "./available-posts.service";
import {Router} from "@angular/router";
import {Article} from "../../model/article.interface";

@Component({
  selector: 'available-posts',
  templateUrl: './available-posts.page.html',
  styleUrls: ['./available-posts.page.scss'],
  host: {'class': 'available-posts'},
  providers: [AvailablePostsService]
})
export class AvailablePostsPage implements OnInit {

  public articles: Array<Article>;

  public noWebService: boolean = false;

  constructor(public availablePostsService: AvailablePostsService, public router : Router) {
  }

  goOnCachedMode() {
    this.router.navigateByUrl("cached-posts");
  }

  toggleCache(article : Article){
    if(article.cached){
      console.log("Je suis plus caché");
      this.availablePostsService.uncacheAnArticle(article);
    }
    else {
      console.log("Je me cache");
      this.availablePostsService.cacheAnArticle(article);
    }
  }

  ngOnInit() {
    console.log("INIT PAGE1");

    this.availablePostsService.getArticles().subscribe(
      (data: Array<Article>) => {
        this.articles = data;
        this.availablePostsService.checkIfCached(this.articles);
      },
      error => {
        this.noWebService = true;
        this.availablePostsService.getCachedArticles().then((cachedArticles: Array<Article>) => {
          cachedArticles.forEach((article:Article) => article.cached = true);
          this.articles = cachedArticles;
        });
      }
    );
  }

  onClickArticle(article : any){
    sessionStorage.setItem("article-to-display", JSON.stringify(article));
    this.router.navigateByUrl("/detailed-post");
  }
}

*/
