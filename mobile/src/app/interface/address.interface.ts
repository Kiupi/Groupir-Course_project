export interface UserAddress { // not Address to avoid conflicts with framwork types
    addressId: number;
    city: string;
    country: string;
    number: string;
    postalCode: string;
    street: string;
    user: any;
}
