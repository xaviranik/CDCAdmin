package com.cdc.cdcadmin;

public class UserInvoiceStrings {

    String serialNum, itemName, itemPrice;

    public UserInvoiceStrings()
    {

    }

    public UserInvoiceStrings(String serialNum, String itemName, String itemPrice)
    {
        this.serialNum = serialNum;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getSerialNum()
    {
        return serialNum;
    }

    public void setSerialNum(String serialNum)
    {
        this.serialNum = serialNum;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemPrice()
    {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice)
    {
        this.itemPrice = itemPrice;
    }
}
