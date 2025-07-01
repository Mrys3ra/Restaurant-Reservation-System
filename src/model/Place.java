/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mary Ruth Batac
 */
public class Place {
    public int id;
    public String name;
    public String email;
    public String Contact;
    public String Date;
    public String Time;
    public int NumberOfGuuests;
    public String Category;
    public String createdby;
    public String appetizer;
    public String mainCo;
    public String dessert;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public int getNumberOfGuuests() {
        return NumberOfGuuests;
    }

    public void setNumberOfGuuests(int NumberOfGuuests) {
        this.NumberOfGuuests = NumberOfGuuests;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getAppetizer() {
        return appetizer != null ? appetizer : "";
    }

    public void setAppetizer(String appetizer) {
        this.appetizer = appetizer;
    }

    public String getMainCo() {
        return mainCo != null ? mainCo : "";
    }

    public void setMainCo(String mainCo) {
        this.mainCo = mainCo;
    }

    public String getDessert() {
        return dessert != null ? dessert : "";
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }
    
    
}
