import {Component, OnInit} from '@angular/core';
import {PaymentMethod} from '../interface/paymentMethod.interface';
import {UserAddress} from '../interface/address.interface';
import {User} from '../interface/user.interface';
import {SessionsServices} from '../sharedServices/sessions.services';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
    selector: 'app-basket-confirm',
    templateUrl: './basket-confirm.page.html',
    styleUrls: ['./basket-confirm.page.scss'],
})

export class BasketConfirmPage implements OnInit {

    public canAccess;
    public addressList: Array<UserAddress>;
    public paymentMethodList: Array<PaymentMethod>;
    public selectedAddress = null;
    public selectedPaymentMethod = null;
    public canConfirm = false;

    constructor(private sessionsService: SessionsServices, private router: Router) {
        this.addressList = new Array<UserAddress>();
        this.paymentMethodList = new Array<PaymentMethod>();
    }

    ngOnInit() {
        const user: User = JSON.parse(localStorage.getItem('user'));

        if (user.role.roleName === 'USER') {
            this.canAccess = true;
            this.sessionsService.getUserAddressList(user.userId).subscribe(
                (data: Array<UserAddress>) => {
                    console.log("addresses:");
                    console.log(data);
                    this.addressList = data;
                }
            );
            this.sessionsService.getUserPaymentMethodList(user.userId).subscribe(
                (data: Array<PaymentMethod>) => {
                    console.log("payment methods:");
                    console.log(data);
                    this.paymentMethodList = data;
                }
            );
        }else{
            this.router.navigateByUrl('');
        }
    }

    selectPaymentMethod(paymentMethod: PaymentMethod) {
        this.selectedPaymentMethod = paymentMethod;
        if (this.selectedAddress !== null)
        {
            this.canConfirm = true;
        }
    }

    selectAddress(address: UserAddress) {
        this.selectedAddress = address;
        if (this.selectedPaymentMethod !== null)
        {
            this.canConfirm = true;
        }
    }

    confirmCommand() {
        console.log("Confirming command... //TODO: impletment this");
    }
}
