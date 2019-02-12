import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.page.html',
  styleUrls: ['./user.page.scss'],
})
export class UserPage implements OnInit {
  public pageNumber = 'toggle-page';
  constructor() { }

  ngOnInit() {
  }

  onToggle(value) {
    this.pageNumber = value;
  }

  goTogglePage() {
    this.pageNumber = 'toggle-page';
  }
}
