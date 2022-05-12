package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import com.example.application.data.service.SpringService;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Item extends AbstractEntity {

    @NotEmpty
    private String groceryListName;

    @NotNull
    private long groceryListID;

    @NotEmpty
    private String itemName = "";

    @NotNull
    private double itemPrice;

    @NotNull
    private double itemQty;

    public Item(GroceryListInfo groceryListInfo) {
        this.groceryListName = groceryListInfo.getGroceryListName();
        this.groceryListID = groceryListInfo.getId();
        this.itemPrice = 0;
        this.itemQty = 0;
    }

    public Item() {
        this.itemPrice = 0;
        this.itemQty = 0;
    }

    public long getGroceryListID() {
        return groceryListID;
    }

    public void setGroceryListID(long groceryListID) {
        this.groceryListID = groceryListID;
    }

    public String getGroceryListName() {
        return groceryListName;
    }

    public void setGroceryListName(String groceryListName) {
        this.groceryListName = groceryListName;
    }

    public Integer getItemId() {
        return this.getId();
    }

    public double getItemQty() {
        return itemQty;
    }

    public void setItemQty(double itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

}
