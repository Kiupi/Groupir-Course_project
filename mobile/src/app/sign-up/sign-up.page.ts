import { Component, OnInit } from '@angular/core';
import {LoadingController, NavController, ToastController} from '@ionic/angular';
import {AuthService} from '../auth.service';
import {NgModel} from '@angular/forms';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.page.html',
  styleUrls: ['./sign-up.page.scss'],
})

export class SignUpPage implements OnInit {

  usernameModel: NgModel;

  constructor(private readonly navCtrl: NavController,
              private readonly authService: AuthService,
              private readonly loadingCtrl: LoadingController,
              private readonly toastCtrl: ToastController) {
  }

  async signup(value: any) {
    const loading = await this.loadingCtrl.create({
      spinner: 'bubbles',
      message: 'Signing up ...'
    });

    loading.present();

    this.authService
        .signup(value)
        .pipe(finalize(() => loading.dismiss()))
        .subscribe(
            jwt => this.showSuccesToast(jwt),
            err => this.handleError(err));
  }

  async handleError(error: any) {
    const message = 'Unexpected error occurred';

    const toast = await this.toastCtrl.create({
      message,
      duration: 5000,
      position: 'bottom'
    });

    toast.present();
  }

  private async showSuccesToast(jwt) {
    if (jwt !== 'EXISTS') {
      const toast = await this.toastCtrl.create({
        message: 'Sign up successful',
        duration: 3000,
        position: 'bottom'
      });

      toast.present();
      this.navCtrl.navigateRoot(['home'], {replaceUrl: true});
    } else {
      const toast = await this.toastCtrl.create({
        message: 'Username already registered',
        duration: 3000,
        position: 'bottom'
      });

      toast.present();

      this.usernameModel.control.setErrors({'usernameTaken': true});
    }
  }
  ngOnInit() {
  }


}

/*
export class SignUpPage implements OnInit {

  public isName = true;
  public isSurname = true;
  public isBirthDate = true;
  public isMail = true;
  public isPassword = true;
  public isConfirmPassword = true;
  public isSamePassword = true;
  public isOldEnough = true;
  constructor() { }

  ngOnInit() {
  }

  onSubmit(name: string, surname: string, birthDate: string, mail: string, password: string, confirmPassword: string) {
    /*
    console.log(mail.match(/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/g));
    if (mail.match(/\w*.?\w*@\w*.{3}[^.| ]/g)) {
      console.log('match');
    }
    this.isName =  name !== '';
    this.isSurname = surname !== '';
    this.isBirthDate = birthDate !== '';
    this.isMail = mail !== '';
    this.isPassword = password !== '';
    this.isConfirmPassword = confirmPassword !== '';
    if (this.isName && this.isSurname && this.isMail && this.isPassword && this.isConfirmPassword && this.isBirthDate) {
      const now = new Date();
      this.isOldEnough = Number(birthDate.split('-')[0]) + 18 <= now.getFullYear();
      if (this.isOldEnough) {
        this.isSamePassword = password === confirmPassword;
        if (this.isSamePassword) {
          console.log('Ca marche !');
        }
      }
    }
  }
}
*/
