package com.cdc.cdcadmin;

public class UserOrderStrings {

    String userPhoneNumber, userAddress, userOrderID;
    int orderStatus;

    public UserOrderStrings(String userPhoneNumber, String userAddress, String userOrderID, int orderStatus)
    {
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userOrderID = userOrderID;
        this.orderStatus = orderStatus;
    }

    public String getUserPhoneNumber()
    {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber)
    {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserAddress()
    {
        return userAddress;
    }

    public void setUserAddress(String userAddress)
    {
        this.userAddress = userAddress;
    }

    public String getUserOrderID()
    {
        return userOrderID;
    }

    public void setUserOrderID(String userOrderID)
    {
        this.userOrderID = userOrderID;
    }

    public int getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus)
    {
        this.orderStatus = orderStatus;
    }
}
