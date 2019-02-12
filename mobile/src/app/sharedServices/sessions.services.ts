import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../auth.service';
import {User} from '../interface/user.interface';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SessionsServices {
    constructor(private httpClient: HttpClient, private readonly authService: AuthService) {

    }

    getConnexionToken(username: String, password: String) {
        const headers = this.authService.setHeadersToken();
        // url : `${environment.serverURL}/api/user/${user.id}/adress`
        // on rajoute values en deuxieme param pour un get
        this.httpClient.post(`${environment.serverURL}/api/user/1/adress/list`, {
            'headers': headers,
            'responseType': 'text'
        })
            .subscribe((data) => {
                console.log(data);
            });
    }
}
