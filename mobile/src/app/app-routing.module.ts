import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './auth.guard';

const routes: Routes = [
    {path: '', redirectTo: 'products', pathMatch: 'full'},
    {path: 'home', loadChildren: './home/home.module#HomePageModule', canActivate: [AuthGuard]},
    {path: 'sign-up', loadChildren: './sign-up/sign-up.module#SignUpPageModule', canActivate: [AuthGuard]},
    {path: 'login', loadChildren: './login/login.module#LoginPageModule'},
    {path: 'forgot-page', loadChildren: './forgot-page/forgot-page.module#ForgotPagePageModule', canActivate: [AuthGuard]},
    {path: 'products', loadChildren: './products/products.module#ProductsPageModule', canActivate: [AuthGuard]},
    {path: 'product-details', loadChildren: './product-details/product-details.module#ProductDetailsPageModule', canActivate: [AuthGuard] },
    {path: 'main-supplier', loadChildren: './main-supplier/main-supplier.module#MainSupplierModule', canActivate: [AuthGuard]},
    {path: 'basket', loadChildren: './basket/basket.module#BasketPageModule'},
    {path: '**', redirectTo: 'login'}



];

@NgModule({
    imports: [
        RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
