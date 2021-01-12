
package com.shubham.odsadmin.model;

public class userList {

    private String Name, Address, Services, Phone;

    public userList(String defaultName, String defaultAddress, String defaultServices, String defaultPhone) {
        Name = defaultName;
        Address = defaultAddress;
        Services = defaultServices;
        Phone = defaultPhone;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getServices() {
        return Services;
    }

    public String getPhone() {
        return Phone;
    }
}
