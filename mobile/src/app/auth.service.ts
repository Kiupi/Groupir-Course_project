import {Injectable} from '@angular/core';
import {Observable, ReplaySubject} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {NavController} from '@ionic/angular';
import {tap} from 'rxjs/operators';
import {environment} from '../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private readonly jwtTokenName = 'jwt_token';


    private authUser = new ReplaySubject<any>(1);


    constructor(private readonly httpClient: HttpClient,
                private readonly navCtrl: NavController) {
    }

    logout() {
        localStorage.removeItem(this.jwtTokenName);
        this.authUser.next(null);
        this.navCtrl.navigateRoot('login', {replaceUrl: true, skipLocationChange: true});
    }

    login(username: any, password: any): Observable<string> {
        const values = {
            email: username,
            password: password
        };
        const headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
        return this.httpClient
            .post(`${environment.serverURL}/api/user/login`, JSON.stringify(values), {headers: headers, responseType: 'text'})
            .pipe(tap(jwt => this.handleJwtResponse(jwt)));
    }

    signup(values: any): Observable<string> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
        return this.httpClient
            .post(`${environment.serverURL}/api/user/signup`, values, {headers: headers, responseType: 'text'})
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
