import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoadingController, NavController, ToastController} from '@ionic/angular';
import {AuthService} from '../auth.service';
import {finalize} from 'rxjs/operators';

@Component({
    selector: 'app-loggin',
    templateUrl: './login.page.html',
    styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

    constructor(public router: Router,
                private readonly navCtrl: NavController,
                private readonly loadingCtrl: LoadingController,
                private readonly authService: AuthService,
                private readonly toastCtrl: ToastController) {
    }

    async login(username: any, password: any) {
        const loading = await this.loadingCtrl.create({
            spinner: 'bubbles',
            message: 'Logging in ...'
        });

        loading.present();

        this.authService
            .login(username, password)
            .pipe(finalize(() => loading.dismiss()))
            .subscribe(
                _ => {
                    this.navCtrl.navigateRoot(['home'], {replaceUrl: true});
                },
                err => {
                    this.handleError(err);
                });
    }

    async handleError(error: any) {
        let message: string;
        if (error.status && error.status === 401) {
            message = 'Login failed';
        } else {
            message = 'Unexpected error: ${error.statusText}';
        }

        const toast = await this.toastCtrl.create({
            message,
            duration: 5000,
            position: 'bottom'
        });

        toast.present();
    }

    ngOnInit() {
    }

    navigate() {
        this.router.navigate(['/forgot-page']);
    }
}
