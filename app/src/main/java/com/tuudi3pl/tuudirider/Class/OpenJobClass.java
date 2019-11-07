package com.tuudi3pl.tuudirider.Class;

public class OpenJobClass {

    private String order_id;
    private String CN;
    private String shipping_type;
    private String date;
    private String status_id;
    private String delivery_type;
    private String delivery_weight;
    private String remarks;
    private String sender_address;
    private String sender_state;
    private String sender_city;
    private String sender_postcode;
    private String sender_country;
    private String receiver_address;
    private String receiver_state;
    private String receiver_city;
    private String receiver_postcode;
    private String receiver_country;
    private String which_one;

    public OpenJobClass(
            String order_id,
            String CN,
            String shipping_type,
            String date,
            String status_id,
            String delivery_type,
            String delivery_weight,
            String remarks,
            String sender_address,
            String sender_state,
            String sender_city,
            String sender_postcode,
            String sender_country,
            String receiver_address,
            String receiver_state,
            String receiver_city,
            String receiver_postcode,
            String receiver_country,
            String which_one
    ){
        this.order_id = order_id;
        this.CN = CN;
        this.shipping_type = shipping_type;
        this.date = date;
        this.status_id = status_id;
        this.delivery_type = delivery_type;
        this.delivery_weight = delivery_weight;
        this.remarks = remarks;
        this.sender_address = sender_address;
        this.sender_state = sender_state;
        this.sender_city = sender_city;
        this.sender_postcode = sender_postcode;
        this.sender_country = sender_country;
        this.receiver_address = receiver_address;
        this.receiver_state = receiver_state;
        this.receiver_city = receiver_city;
        this.receiver_postcode = receiver_postcode;
        this.receiver_country = receiver_country;
        this.which_one = which_one;
    }

    public String getWhich_one() {
        return which_one;
    }

    public String getDate() {
        return date;
    }

    public String getCN() {
        return CN;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public String getDelivery_weight() {
        return delivery_weight;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getSender_address() {
        return sender_address;
    }

    public String getSender_city() {
        return sender_city;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public String getSender_country() {
        return sender_country;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public String getSender_postcode() {
        return sender_postcode;
    }

    public String getSender_state() {
        return sender_state;
    }

    public String getReceiver_postcode() {
        return receiver_postcode;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getReceiver_country() {
        return receiver_country;
    }

}
