import {Component, OnInit} from '@angular/core';
import {OrderItem} from '../interface/orderItem.interface';
import {SessionsServices} from '../sharedServices/sessions.services';

@Component({
    selector: 'app-acceuil-fournisseur',
    templateUrl: './acceuil-fournisseur.page.html',
    styleUrls: ['./acceuil-fournisseur.page.scss'],
})
export class AcceuilFournisseurPage implements OnInit {

    public orderItems: Array<OrderItem>;

    constructor(private sessionsService: SessionsServices) {
    }

    ngOnInit() {
    }

    public push() {
        this.sessionsService.getOrderItems().subscribe(
            (data) => {
                console.log(data);
            }
        );
    }
}
