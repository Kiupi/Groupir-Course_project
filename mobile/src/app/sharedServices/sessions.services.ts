import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from '../auth.service';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SessionsServices {

    private readonly jwtTokenName = 'jwt_token';

    constructor(private readonly httpClient: HttpClient, private readonly authService: AuthService) {

    }

    getOrderItems(): Observable<any> {
        const jwt = localStorage.getItem(this.jwtTokenName);
        const token = JSON.parse(jwt).token;

        const headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization', 'Bearer ' + token);

        return this.httpClient.get(`${environment.serverURL}/api/supplier/items`, {headers: headers, responseType: 'text'});
    }

}
