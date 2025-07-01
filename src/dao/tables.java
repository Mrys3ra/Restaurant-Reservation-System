/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.swing.JOptionPane;

/**
 *
 * @author Mary Ruth Batac
 */
public class tables {
     public static void main(String []args){
      try{
      // String userTable = "create table user(id int AUTO_INCREMENT primary key, fname varchar(200), lname varchar(200),contact varchar(11), email varchar(200), password varchar(50), status varchar(20), UNIQUE(email))";
        String adminDetails = "Insert into user(fname,lname,contact,email,password) values('Admin','me','09123456789','admin@gmail.com','admin')";
        DbOperations.setDataOrDelete(adminDetails, "Admin Details Added Successfully!");
       // DbOperations.setDataOrDelete(userTable, "User Table Created Successfully"); 
        
      String PlaceTable = "create table Place (id int Primary key, name varchar (200),email varchar(200), Contact varchar(11), date varchar(200), time varchar(200), NumberofGuuests varchar(50), createdby varchar(200), appetizer varchar(200),mainCo varchar (200),dessert varchar (200))";
      DbOperations.setDataOrDelete(PlaceTable, "Place Table Created Successfully");
        }
      catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
      }
     }
}
