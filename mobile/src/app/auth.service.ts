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
        return this.httpClient.post(`${environment.serverURL}/api/user/login`, JSON.stringify(values), {
            headers: headers,
            responseType: 'text'
        })
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

    hasAccess(): Promise<boolean> {
        const jwt = localStorage.getItem(this.jwtTokenName);
        return this.checkToken();
    }

    private checkToken(): Promise<boolean> {
        const headers = this.setHeadersToken();
        return new Promise((resolve, _) => {
            this.getCurrentUser()
                .subscribe((data) => {
                        localStorage.setItem("user",data);
                        console.log('token ok');
                        resolve(true);
                    },
                    err => {
                        console.log('token expired or does not exist');
                        this.logout();
                        resolve(false);
                    });
        });
    }

    public setHeadersToken(): HttpHeaders | null {

        const jwt = localStorage.getItem(this.jwtTokenName);
        if (jwt) {
            const token = JSON.parse(jwt).token;

            return new HttpHeaders().set('Content-Type', 'application/json').set('Authorization', 'Bearer ' + token);
        } else {
            return null;
        }

    }

    getCurrentUser(): Observable<any>{
        const headers = this.setHeadersToken();
        return this.httpClient.get(`${environment.serverURL}/api/user/currentUser`, {headers: headers, responseType: 'text'});
    }
}
