import { Component, OnInit } from '@angular/core';
import {User} from "../../../interface/user.interface";

@Component({
  selector: 'app-adress-list',
  templateUrl: './adress-list.component.html',
  styleUrls: ['./adress-list.component.scss']
})
export class AdressListComponent implements OnInit {
  user: User;
  constructor() { }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem("user"));
  }

}