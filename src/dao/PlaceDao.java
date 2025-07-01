    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package dao;

    import javax.swing.JOptionPane;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import model.Place;

    public class PlaceDao {



        public static String getId() {
            int id = 1;
            try {
                ResultSet rs = DbOperations.getData("select max(id) from Place");
                if (rs.next()) {
                    id = rs.getInt(1);
                    id = id + 1;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }

            return String.valueOf(id);
        }

        public static void save(Place place) {
            String query = "insert into Place (name, email, contact, date, time, numberofguuests, category, createdby, appetizer, mainCo, dessert) values('" + place.getName() + "','" + place.getEmail() + "','" + place.getContact() + "','" + place.getDate() + "','" + place.getTime() + "','" + place.getNumberOfGuuests() + "','" + place.getCategory() + "','" + place.getCreatedby() + "','" + place.getAppetizer() + "','" + place.getMainCo() + "','" + place.getDessert() + "')";
            DbOperations.setDataOrDelete(query, "Reservation Added Successfully");
        }



      public static List<Place> getAllPlaces() {
            List<Place> places = new ArrayList<>();
            try {
                String query = "select * from Place";
                ResultSet rs = DbOperations.getData(query);
                while (rs.next()) {
                    Place place = new Place();
                   // place.setId(rs.getString("id"));
                    place.setName(rs.getString("name"));
                    place.setEmail(rs.getString("email"));
                    place.setContact(rs.getString("contact"));
                    place.setDate(rs.getString("date"));
                    place.setTime(rs.getString("time"));
                    place.setNumberOfGuuests(rs.getInt("numberOfGuuests"));
                    place.setCategory(rs.getString("category"));
                    place.setCreatedby(rs.getString("createdBy"));
                    place.setAppetizer(rs.getString("appetizer"));
                    place.setMainCo(rs.getString("mainCo"));
                    place.setDessert(rs.getString("dessert"));

                    places.add(place);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            return places;
        }


        public static ArrayList<Place> getAllRecordsByInc(String date){
            ArrayList<Place> arrayList = new ArrayList<>();
            try{
                ResultSet rs = DbOperations.getData("select *from place where date like '%"+date+"%' order by date ASC"); // no order by date ASC
                while(rs.next()){
                Place place = new Place();
               // place.setId(rs.getString("id"));
                place.setName(rs.getString("name"));
                place.setEmail(rs.getString("email"));
                place.setContact(rs.getString("contact"));
                place.setDate(rs.getString("date"));
                place.setTime(rs.getString("time"));
                place.setCategory(rs.getString("category"));
                place.setNumberOfGuuests(rs.getInt("guest"));
                place.setCreatedby(rs.getString("createdBy"));
                place.setAppetizer(rs.getString("appetizer"));
                place.setMainCo(rs.getString("mainCo"));
                place.setDessert(rs.getString("dessert"));
                arrayList.add(place);
                }
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(null,e);
            }
            return arrayList;
        }
        public static ArrayList<Place> getAllRecordsByDesc(String date){
            ArrayList<Place> arrayList = new ArrayList<>();
            try{
                ResultSet rs = DbOperations.getData("select *from place where date like '%"+date+"%'order By name DESC"); // id to name
                while(rs.next()){
                Place place = new Place();
               // place.setId(rs.getString("id"));
                place.setName(rs.getString("name"));
                place.setEmail(rs.getString("email"));
                place.setContact(rs.getString("contact"));
                place.setDate(rs.getString("date"));
                place.setTime(rs.getString("time"));
                place.setCategory(rs.getString("category"));
                place.setNumberOfGuuests(rs.getInt("NumberOfGuuests"));
                place.setCreatedby(rs.getString("createdBy"));
                place.setAppetizer(rs.getString("appetizer"));
                place.setMainCo(rs.getString("mainCo"));
                place.setDessert(rs.getString("dessert"));
                arrayList.add(place);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
            return arrayList;
        }


        public static Place getPlaceById(int id) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public static void update(Place place) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    public static ArrayList<Place> getAllRecords() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    }
