import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouteReuseStrategy} from '@angular/router';

import {IonicModule, IonicRouteStrategy} from '@ionic/angular';
import {SplashScreen} from '@ionic-native/splash-screen/ngx';
import {StatusBar} from '@ionic-native/status-bar/ngx';

import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {CommonModule} from '@angular/common';
import { DetailComponent } from './profil/detail/detail.component';
import { AddressListComponent } from './profil/address-list/address-list.component';
import { PurchaseListComponent } from './profil/purchase-list/purchase-list.component';
import { PaymentListComponent } from './profil/payment-list/payment-list.component';

@NgModule({
    declarations: [
        AppComponent,
        DetailComponent,
        AddressListComponent,
        PurchaseListComponent,
        PaymentListComponent
    ],
    entryComponents: [],
    imports: [
        HttpClientModule,
        CommonModule,
        BrowserModule,
        IonicModule.forRoot(),
        AppRoutingModule
    ],
    providers: [
        StatusBar,
        SplashScreen,
        {provide: RouteReuseStrategy, useClass: IonicRouteStrategy}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
