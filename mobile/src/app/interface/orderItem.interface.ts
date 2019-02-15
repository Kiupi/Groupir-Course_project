export interface OrderItem {
    orderId: number;

    /*  contain :
    Manufacturer Reference
    optionId
    optionName
    productId
    productName
 */
    option: any;

    /* contain :
    adressId
    city
    country
    number
    postal code
    street
    user
     */
    orderAddress: any;

    dispatchmentDate?: any;
    quantity: number;
    trackingNumber: string;
}
