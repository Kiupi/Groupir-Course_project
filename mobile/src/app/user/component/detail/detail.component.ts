import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, ReplaySubject} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  private readonly jwtTokenName = 'jwt_token';
  private authUser = new ReplaySubject<any>(1);
  constructor(private readonly httpClient: HttpClient) { }
  ngOnInit() {
    console.log(this.coucou());
  }

  coucou(): Observable<Object> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.httpClient
        .post(`${environment.serverURL}/api/user/1/address/list`, {headers: headers, responseType: 'text'})
        .pipe(tap(jwt => {
          if (jwt !== 'EXIST') {
            return this.handleJwtResponse(jwt);
          }
          return jwt;
        }));
  }

  private handleJwtResponse(jwt: string): string {
    localStorage.setItem(this.jwtTokenName, jwt);
    this.authUser.next(jwt);

    return jwt;
  }
}
