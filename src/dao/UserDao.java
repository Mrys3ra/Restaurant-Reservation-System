/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author Mary Ruth Batac
 */
public class UserDao {
    public static void save(User user){
        user.setStatus("true");
        String query = "insert into user(fname, lname, contact, email, password, status)values('" 
            + user.getFname() + "','" + user.getLname() + "','" + user.getContact() + "','" 
            + user.getEmail() + "','" + user.getPassword() + "','" + user.getStatus() + "')";
        DbOperations.setDataOrDelete(query, "Registered Successfully!");
    }

    
     public static User login (String email, String password){
  User user = null;
        try {
            ResultSet rs = DbOperations.getData("Select *from user where email = '" + email + "' and password='" + password + "'");
            while (rs.next()) {
                user = new User();
                user.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return user;
    }

   public static boolean emailExistsWithDifferentPassword(String email, String password) {
        try {
            ResultSet rs = DbOperations.getData("SELECT password FROM user WHERE email = '" + email + "'");
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return !storedPassword.equals(password);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
    
}
