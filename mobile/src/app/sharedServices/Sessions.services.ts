import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn : 'root'
})
export class SessionsServices {
    constructor(private http: HttpClient){

    }

    getConnexionToken(username : String, password: String){

    }
}
