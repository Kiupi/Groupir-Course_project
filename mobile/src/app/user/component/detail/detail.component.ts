import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, ReplaySubject} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {tap} from 'rxjs/operators';
import {AuthService} from '../../../auth.service';
import {User} from "../../../interface/user.interface";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  user: User;
  constructor(private httpClient: HttpClient, private readonly authService: AuthService) { }
  ngOnInit() {
    this.coucou();
    this.user = JSON.parse(localStorage.getItem("user"));
  }

  coucou() {
    const headers = this.authService.setHeadersToken();
    console.log(headers);
    // url : `${environment.serverURL}/api/user/${user.id}/adress`
    // on rajoute values en deuxieme param pour un post
    this.httpClient.get(`${environment.serverURL}/api/user/1/address/list`, {
      'headers': headers,
      'responseType': 'text'
    })
        .subscribe((data) => {
          console.log(data);
          this.user = JSON.parse(data);
          console.log(this.user);
        });
  }
}
