
package com.shubham.odsadmin.model;

public class workerList {

    private String Name, Address, Services, Ratings, Phone;

    public workerList(String defaultName, String defaultAddress, String defaultServices, String defaultRatings, String defaultPhone) {
        Name = defaultName;
        Address=defaultAddress;
        Services=defaultServices;
        Ratings=defaultRatings;
        Phone=defaultPhone;
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

    public String getRatings() {
        return Ratings;
    }

    public String getPhone() {
        return Phone;
    }
}
