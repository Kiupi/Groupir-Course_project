import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { AcceuilFournisseurPage } from './acceuil-fournisseur.page';

const routes: Routes = [
  {
    path: '',
    component: AcceuilFournisseurPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [AcceuilFournisseurPage]
})
export class AcceuilFournisseurPageModule {}
