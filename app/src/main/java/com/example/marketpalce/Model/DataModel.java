package com.example.marketpalce.Model;

import java.util.Date;

public class DataModel {
    private int listingid,user_Id;
    private String item_title,item_categories,item_condition,item_description,item_location,item_cod,item_postage,item_image;
    private double item_price;
    private String created_date;

    public int getListingid() {
        return listingid;
    }

    public void setListingid(int listingid) {
        this.listingid = listingid;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_categories() {
        return item_categories;
    }

    public void setItem_categories(String item_categories) {
        this.item_categories = item_categories;
    }

    public String getItem_condition() {
        return item_condition;
    }

    public void setItem_condition(String item_condition) {
        this.item_condition = item_condition;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_location() {
        return item_location;
    }

    public void setItem_location(String item_location) {
        this.item_location = item_location;
    }

    public String getItem_cod() {
        return item_cod;
    }

    public void setItem_cod(String item_cod) {
        this.item_cod = item_cod;
    }

    public String getItem_postage() {
        return item_postage;
    }

    public void setItem_postage(String item_postage) {
        this.item_postage = item_postage;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
