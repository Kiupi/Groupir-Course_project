import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';
import {MainSupplierPage} from './main-supplier.page';


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
        RouterModule.forChild(routes)
    ],
    declarations: [MainSupplierPage]
})
export class MainSupplierModule {
}
