import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuard} from './auth.guard';

const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'home', loadChildren: './home/home.module#HomePageModule', canActivate: [AuthGuard]},
    {path: 'sign-up', loadChildren: './sign-up/sign-up.module#SignUpPageModule'},
    {path: 'login', loadChildren: './login/login.module#LoginPageModule'},
    {path: 'forgot-page', loadChildren: './forgot-page/forgot-page.module#ForgotPagePageModule'},
    {path: 'products', loadChildren: './products/products.module#ProductsPageModule'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
