/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Mary Ruth Batac
 */
public class DbOperations {
     public static void setDataOrDelete(String Query, String msg){
       try{
          Connection con = ConnectionProvider.getCon();
          Statement st = con.createStatement();
          st.executeUpdate(Query);
          if (!msg.equals(""))
              JOptionPane.showMessageDialog(null, msg);
       }
       catch(Exception e){
         JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
       }
    }
     
     public static ResultSet getData(String query){
      try{
        Connection con = ConnectionProvider.getCon();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
      }
      catch(Exception e){
        JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
        return null;
      }
    }

    static dao.Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
