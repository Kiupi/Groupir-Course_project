import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-loggin',
  templateUrl: './loggin.page.html',
  styleUrls: ['./loggin.page.scss'],
})
export class LogginPage implements OnInit {

  constructor(public router:Router) { }

  ngOnInit() {
  }

  navigate(){
    this.router.navigate(['/forgot-page'])
  }
}
