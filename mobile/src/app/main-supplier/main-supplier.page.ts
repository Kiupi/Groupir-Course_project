import {Component, OnInit} from '@angular/core';
import {OrderItem} from '../interface/orderItem.interface';
import {SessionsServices} from '../sharedServices/sessions.services';

@Component({
    selector: 'app-main-supplier',
    templateUrl: './main-supplier.page.html',
    styleUrls: ['./main-supplier.page.scss'],
})
export class MainSupplierPage implements OnInit {

    public orderItems: Array<OrderItem>;

    constructor(private sessionsService: SessionsServices) {
    }

    ngOnInit() {
        this.sessionsService.getOrderItems().subscribe(
            (data: Array<OrderItem>) => {
                this.orderItems = data;
            }
        );
    }

}
