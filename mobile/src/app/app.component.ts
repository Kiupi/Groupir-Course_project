import { Component,OnInit } from '@angular/core';

import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent{

  title : 'app';
  restItems: any;

  //TODO : change API URL
  restItemsUrl: 'https://public-api.wordpress.com/rest/v1.1/sites/vocon-it.com/posts';

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private http: HttpClient
  ) {
    this.initializeApp();
  }

  ngOnInit(){
  }


  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }
}
