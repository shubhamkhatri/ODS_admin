
package com.shubham.odsadmin.model;

public class billList {

    private String Phone, Type, Date, Amount,Id;

    public billList(String defaultPhone, String defaultType, String defaultDate, String defaultAmount,String defaultId) {
        Phone=defaultPhone;
        Type=defaultType;
        Date=defaultDate;
        Amount=defaultAmount;
        Id=defaultId;
    }

    public String getPhone() {
        return Phone;
    }

    public String getType() {
        return Type;
    }

    public String getDate() {
        return Date;
    }

    public String getAmount() {
        return Amount;
    }

    public String getId() {
        return Id;
    }
}
