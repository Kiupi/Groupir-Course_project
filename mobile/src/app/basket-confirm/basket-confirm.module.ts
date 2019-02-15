import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';
import {BasketConfirmPage} from './basket-confirm.page';


const routes: Routes = [
    {
        path: '',
        component: BasketConfirmPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RouterModule.forChild(routes)
    ],
    declarations: [BasketConfirmPage]
})
export class BasketConfirmModule {
}
