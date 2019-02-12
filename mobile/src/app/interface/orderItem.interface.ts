export interface OrderItem {
    orderItemId: number;

    /*  contain :
    Manufacturer Reference
    optionId
    optionName
    productId
    productName
 */
    productOption: any;

    /* contain :
    adressId
    city
    country
    number
    postal code
    street
    user
     */
    orderAdress: any;

    orderDate: Date;
    expeditionDate?: Date;
    quantity: number;
    trackingNumber: string;
}
