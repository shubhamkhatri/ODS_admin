
package com.shubham.odsadmin.model;

import com.google.type.DateTime;

public class bookingList {

    private String Id, User, Worker, Service, DateTime, CarType, Price, Status;

    public String getUser() {
        return User;
    }

    public String getWorker() {
        return Worker;
    }

    public String getService() {
        return Service;
    }

    public String getDateTime() {
        return DateTime;
    }

    public String getCarType() {
        return CarType;
    }

    public String getPrice() {
        return Price;
    }

    public String getId() {
        return Id;
    }

    public String getStatus() {
        return Status;
    }

    public bookingList(String defaultId, String defaultUser, String defaultWorker, String defaultService, String defaultDateTime, String defaultCarType, String defaultPrice, String defaultStatus) {

        Id = defaultId;
        User = defaultUser;
        Worker = defaultWorker;
        Service = defaultService;
        DateTime = defaultDateTime;
        CarType = defaultCarType;
        Price = defaultPrice;
        Status = defaultStatus;
    }

}
