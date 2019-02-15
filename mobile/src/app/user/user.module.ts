import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { UserPage } from './user.page';
import {DetailComponent} from './component/detail/detail.component';
import { AdressListComponent } from './component/adress-list/adress-list.component';
import { PaymentListComponent } from './component/payment-list/payment-list.component';
import { PurchaseHistoryComponent } from './component/purchase-history/purchase-history.component';
import { TogglePageComponent } from './component/toggle-page/toggle-page.component';

const routes: Routes = [
  {
    path: '',
    component: UserPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [UserPage, DetailComponent, AdressListComponent, PaymentListComponent, PurchaseHistoryComponent, TogglePageComponent],
  entryComponents: [UserPage, DetailComponent, AdressListComponent, PaymentListComponent, TogglePageComponent]
})
export class UserPageModule {}
