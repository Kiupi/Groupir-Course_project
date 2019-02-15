import {Component, OnInit} from '@angular/core';
import {OrderItem} from '../interface/orderItem.interface';
import {SessionsServices} from '../sharedServices/sessions.services';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
    selector: 'app-main-supplier',
    templateUrl: './main-supplier.page.html',
    styleUrls: ['./main-supplier.page.scss'],
})
export class MainSupplierPage implements OnInit {

    public orderItems: Array<OrderItem>;
    public orderItemsSent: Array<OrderItem>;
    public orderItemsNew: Array<OrderItem>;
    public new = true;
    public canAccess;

    constructor(private sessionsService: SessionsServices, private router: Router) {
        this.orderItemsNew = new Array<OrderItem>();
        this.orderItemsSent = new Array<OrderItem>();
    }

    ngOnInit() {
        const user = JSON.parse(localStorage.getItem('user'));

        if (user.role.roleName === 'SUPPLIER' || user.role.roleName === 'ADMIN') {
            this.canAccess = true;
            this.sessionsService.getOrderItems().subscribe(
                (data: Array<OrderItem>) => {
                    console.log(data);
                    this.orderItems = data;
                    console.log(data);
                    this.orderItems.forEach((orderItem) => {
                        console.log(orderItem);
                        if (orderItem.dispatchmentDate !== null) {
                            orderItem.dispatchmentDate = new Date(orderItem.dispatchmentDate * 1000);
                            console.log(orderItem.dispatchmentDate);
                            this.orderItemsSent.push(orderItem);
                        } else {
                            this.orderItemsNew.push(orderItem);
                        }
                    });
                }
            );
        }else{
            this.router.navigateByUrl('');
        }
    }
    validateSend(form: NgForm, orderItem: OrderItem) {
        this.orderItems.slice(this.orderItems.indexOf(orderItem));
        orderItem.dispatchmentDate = form.value['dispatchmentDate'];
        orderItem.trackingNumber = form.value['trackingNumber'];
        console.log(orderItem);
        const values = {
            dispatchmentDate: orderItem.dispatchmentDate,
            optionId: orderItem.option.optionId,
            orderId: orderItem.orderId,
            trackingNumber: orderItem.trackingNumber
        };
        this.sessionsService.sendOrderItem(values).subscribe(
            () => {
                console.log('ok');
            },
            err => {
                console.log(err);
            }
        );
        this.ngOnInit();
    }


    switchToNew() {
        console.log('goToNew');
        this.new = true;
    }

    switchToSend() {
        console.log('goToSend');
        this.new = false;
    }
}
