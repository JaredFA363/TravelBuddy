/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jared
 */
public class DatabaseHandler {
    
    public String insertUser(String JsonData){
        Connection mycon = null;
        Statement statement = null;
        
        RandomNumberService rn = new RandomNumberService();
        RandomNumGetterSetter rngs = rn.getRandomID();
        int in_id = rngs.getRandom();
        
        Gson gson = new Gson();
        UserData userData = gson.fromJson(JsonData, UserData.class);
        
        String in_username = userData.getUsername();
        String in_name = userData.getName();
        String in_password = userData.getPassword();
        
        String query = "INSERT INTO USERS (id, username, name, password) VALUES ("+in_id+",'"+in_username+"','"+in_name+"','"+in_password+"')";
        String response = "Error"; 
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            statement.executeUpdate(query);
            response = "Success";
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            response = e.getMessage();
        }
        
        Result result = new Result(response);
        var jsonResponse = gson.toJson(result, Result.class);
        return jsonResponse;
        
        //return response;
    }
    
    public String queryUser(String in_username, String in_password){
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM users WHERE username = '"+in_username+"'";
        String response = "Error"; 
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String password = rs.getString("password");
                //System.out.println(id + " " + username + " " + name + " " + password);
                //response = username + " " + name + " " + password;
                if (in_password.equals(password)){
                    response = "Success";
                }
                else{
                    response = "Not matching in_password = "+in_password +" password = "+password;
                }
            }
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            response = e.getMessage();
        }
        
        Result result = new Result(response);
        Gson gson = new Gson();
        var jsonResponse = gson.toJson(result, Result.class);
        return jsonResponse;
        
        //return response;
    }
    
    public String queryTrips(String in_location_from, String in_location_to){
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM TRIP WHERE location_from = '"+in_location_from+"' AND location_to = '"+in_location_to+"'";
        String response = "Error"; 
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String password = rs.getString("password");
                System.out.println(id + " " + username + " " + name + " " + password);
                response = username + " " + name + " " + password;
            }
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            response = e.getMessage();
        }
        
        Result result = new Result(response);
        Gson gson = new Gson();
        var jsonResponse = gson.toJson(result, Result.class);
        return jsonResponse;
    }
    
    public String insertTrip(String JsonData){
        Connection mycon = null;
        Statement statement = null;
        
        RandomNumberService rn = new RandomNumberService();
        RandomNumGetterSetter rngs = rn.getRandomID();
        int in_id = rngs.getRandom();
        
        Gson gson = new Gson();
      
        TripData tripData = gson.fromJson(JsonData, TripData.class);
        int user_id = tripData.getUserId();
        String date_from = tripData.getDateFrom();
        String date_to = tripData.getDateTo();
        String location_from = tripData.getLocationFrom();
        String location_to = tripData.getLocationTo();
        String weather_description = tripData.getWeatherDescription();
        
        //Date date_from1 = convertStringToSqlDate(date_from);
        //Date date_to1 = convertStringToSqlDate(date_to);
        
        String query = "INSERT INTO TRIP (trip_id, user_id, date_from, date_to, location_from, location_to, weather) VALUES ("+in_id+","+user_id+",'"+date_from+"','"+date_to+"','"+location_from+"','"+location_to+"','"+weather_description+"')";
        String response = "Error"; 
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            statement.executeUpdate(query);
            response = "Success";
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            response = e.getMessage();
        }
        
        Result result = new Result(response);
        var jsonResponse = gson.toJson(result, Result.class);
        return jsonResponse;
    }
    
    public String expressInterest(String JsonData){
        Connection mycon = null;
        Statement statement = null;
        
        RandomNumberService rn = new RandomNumberService();
        RandomNumGetterSetter rngs = rn.getRandomID();
        int in_id = rngs.getRandom();
        
        Gson gson = new Gson();
        
        InterestData interestData = gson.fromJson(JsonData, InterestData.class);
        int trip_id = interestData.getTrip_id();
        int proposed_user_id = interestData.getProposed_user_id();
        int new_user_id = interestData.getNew_user_id();
        
        String query = "INSERT INTO INTERESTED (interest_id, trip_id, proposed_user_id, new_user_id, accepted) VALUES ("+in_id+","+trip_id+","+proposed_user_id+","+new_user_id+",'NO')";
        String response = "Error"; 
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            statement.executeUpdate(query);
            response = "Success";
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            response = e.getMessage();
        }
        
        Result result = new Result(response);
        var jsonResponse = gson.toJson(result, Result.class);
        return jsonResponse;
    }
    
    /*public static void main(String args[]){
        DatabaseHandler db = new DatabaseHandler();
        //String and = db.queryUser("u","u");
        //System.out.println(and);
        //System.out.println(db.convertStringToSqlDate("20231227"));
        JsonObject json = new JsonObject();
        json.addProperty("trip_id", 9982);
        json.addProperty("proposed_user_id", 1);
        json.addProperty("new_user_id", 1);
  
        //json.addProperty("userId", 1);
        //json.addProperty("dateFrom", "2023-12-28");
        //json.addProperty("dateTo", "2023-12-29");
        //json.addProperty("locationFrom", "london");
        //json.addProperty("locationTo", "nottingham");
        //WeatherService ws = new WeatherService();
        //String weather = ws.weatherJson("nottingham","2023-12-28","2023-12-29").toString();
        //json.addProperty("weatherDescription", weather);
        
        System.out.println(json.toString());
        
        //db.insertTrip(json.toString());
        db.expressInterest(json.toString());
    }*/
}