import {Injectable} from '@angular/core';
import {Observable, ReplaySubject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {NavController} from '@ionic/angular';
import {JwtHelperService} from '@auth0/angular-jwt';
import {tap} from 'rxjs/operators';
import {environment} from '../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private readonly jwtTokenName = 'jwt_token';

    private authUser = new ReplaySubject<any>(1);
    public authUserObservable = this.authUser.asObservable();


    constructor(private readonly httpClient: HttpClient,
                private readonly navCtrl: NavController,
                private readonly jwtHelper: JwtHelperService) {
    }

    hasAccess(): Promise<boolean> {
        const jwt = localStorage.getItem(this.jwtTokenName);

        if (jwt && !this.jwtHelper.isTokenExpired(jwt)) {
            return new Promise((resolve, _) => {
                this.httpClient.get(`${environment.serverURL}/authenticate`)
                    .subscribe(() => {
                            this.authUser.next(jwt);
                            resolve(true);
                        },
                        err => {
                            this.logout();
                            resolve(false);
                        });
            });
        } else {
            this.logout();
            return Promise.resolve(false);
        }
    }

    logout() {
        localStorage.removeItem(this.jwtTokenName);
        this.authUser.next(null);
        this.navCtrl.navigateRoot('login', {replaceUrl: true, skipLocationChange: true});
    }

    login(username: any, password: any): Observable<string> {
        let values = {
            email: username,
            password: password
        };
        return this.httpClient.post(`${environment.serverURL}/api/user/login`, JSON.stringify(values), {responseType: 'text'})
            .pipe(tap(jwt => this.handleJwtResponse(jwt)));
    }

    signup(values: any): Observable<string> {
        return this.httpClient.post(`${environment.serverURL}/api/user/signup`, values, {responseType: 'text'})
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
