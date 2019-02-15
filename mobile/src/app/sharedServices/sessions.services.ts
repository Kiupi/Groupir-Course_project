import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from '../auth.service';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {User} from "../interface/user.interface";
import { of as observableOf} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SessionsServices {

    private readonly jwtTokenName = 'jwt_token';

    constructor(private readonly httpClient: HttpClient, private readonly authService: AuthService) {

    }

    public getOrderItems(): Observable<any> {
        const jwt = localStorage.getItem(this.jwtTokenName);
        const token = JSON.parse(jwt).token;

        const headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization', 'Bearer ' + token);

        return this.httpClient.get(`${environment.serverURL}/api/supplier/items`, {headers: headers});
    }

    public sendOrderItem(value: any): Observable<any> {
        const jwt = localStorage.getItem(this.jwtTokenName);
        const token = JSON.parse(jwt).token;

        const headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization', 'Bearer ' + token);
        return this.httpClient.post(`${environment.serverURL}/api/supplier/items/${value.orderId}/${value.optionId}`,
            JSON.stringify(value), {headers: headers});
    }

    public getUserAddressList(userId: number): Observable<any> {
        const jwt = localStorage.getItem(this.jwtTokenName);
        const token = JSON.parse(jwt).token;

        const headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization', 'Bearer ' + token);
        //return observableOf([]);
        return this.httpClient.get(`${environment.serverURL}/api/user/${userId}/address/list`, {headers: headers});
    }

    public getUserPaymentMethodList(userId: number): Observable<any> {
        const jwt = localStorage.getItem(this.jwtTokenName);
        const token = JSON.parse(jwt).token;

        const headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization', 'Bearer ' + token);
        //return observableOf([]);
        return this.httpClient.get(`${environment.serverURL}/api/user/${userId}/payment-method/list`, {headers: headers});
    }
}
