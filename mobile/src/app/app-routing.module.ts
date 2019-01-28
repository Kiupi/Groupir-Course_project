import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'loggin', pathMatch: 'full' },
  { path: 'home', loadChildren: './home/home.module#HomePageModule' },
  { path: 'sign-up', loadChildren: './sign-up/sign-up.module#SignUpPageModule' },
  { path: 'loggin', loadChildren: './loggin/loggin.module#LogginPageModule' },
  { path: 'forgot-page', loadChildren: './forgot-page/forgot-page.module#ForgotPagePageModule' },
  { path: 'products', loadChildren: './products/products.module#ProductsPageModule' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
