import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {MainSupplierPage} from './main-supplier.page';
import {MainSupplierRoutingModule} from './main-supplier.router.module';


const routes: Routes = [
    {
        path: '',
        component: MainSupplierPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        MainSupplierRoutingModule
    ],
    declarations: [MainSupplierPage]
})
export class MainSupplierModule {
}
