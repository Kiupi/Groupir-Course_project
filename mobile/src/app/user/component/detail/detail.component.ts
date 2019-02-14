import { Component, OnInit } from '@angular/core';
import {User} from "../../../interface/user.interface";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  user: User;
  constructor() { }
  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem("user"));
  }
}
