package com.cdc.cdcadmin;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class UserOrderInfo {
    boolean accepted, tracking, completed;
    String userPhoneNumber, userAddress, totalItems, totalPrice;
    @ServerTimestamp
    Date timeStamp;


    public UserOrderInfo()
    {

    }


    public UserOrderInfo(boolean accepted, boolean tracking, boolean completed, String userPhoneNumber, String userAddress, String totalItems, String totalPrice, Date timeStamp)
    {
        this.accepted = accepted;
        this.tracking = tracking;
        this.completed = completed;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
        this.timeStamp = timeStamp;
    }

    public boolean isAccepted()
    {
        return accepted;
    }

    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    public boolean isTracking()
    {
        return tracking;
    }

    public void setTracking(boolean tracking)
    {
        this.tracking = tracking;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
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

    public String getTotalItems()
    {
        return totalItems;
    }

    public void setTotalItems(String totalItems)
    {
        this.totalItems = totalItems;
    }

    public String getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp)
    {
        this.timeStamp = timeStamp;
    }
}
