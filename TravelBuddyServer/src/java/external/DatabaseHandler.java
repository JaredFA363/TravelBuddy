/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.*;

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
        String response = "Error - from DB"; 
        
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
    
    public String queryUser(String JsonData){
        Gson gson = new Gson();
        LoginData loginData = gson.fromJson(JsonData, LoginData.class);
        
        String in_username = loginData.getUsername();
        String in_password = loginData.getPassword();
        
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM USERS WHERE username = '"+in_username+"'";
        String response = "Error - from DB"; 
        
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
        var jsonResponse = gson.toJson(result, Result.class);
        return jsonResponse;
        
    }
    
    public String queryTrips(String in_location_from, String in_location_to){
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM TRIP WHERE location_from = '"+in_location_from+"' AND location_to = '"+in_location_to+"'";
        String response = "Error - from DB"; 
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);
         
            JsonArray tripsArray = new JsonArray();

            while (rs.next()) {
                JsonObject tripObject = new JsonObject();
                tripObject.addProperty("trip_id", rs.getInt("trip_id"));
                tripObject.addProperty("date_from", rs.getString("date_from"));
                tripObject.addProperty("date_to", rs.getString("date_to"));
                tripObject.addProperty("location_from", rs.getString("location_from"));
                tripObject.addProperty("location_to", rs.getString("location_to"));
                tripObject.addProperty("weather", rs.getString("weather"));

                tripsArray.add(tripObject);
            }

            response = tripsArray.toString();
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
        
        WeatherService ws = new WeatherService();
        String weather = ws.weatherJson(location_to,date_from,date_to).toString();
        
        
        String query = "INSERT INTO TRIP (trip_id, user_id, date_from, date_to, location_from, location_to, weather) VALUES ("+in_id+","+user_id+",'"+date_from+"','"+date_to+"','"+location_from+"','"+location_to+"','"+weather+"')";
        String response = "Error - from DB"; 
        
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
        String response = "Error - from DB"; 
        
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
    
    public String getAllTrips() {
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM TRIP";
        String response = "Error - from DB";

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel", "j", "j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);

            JsonArray tripsArray = new JsonArray();

            while (rs.next()) {
                JsonObject tripObject = new JsonObject();
                tripObject.addProperty("trip_id", rs.getInt("trip_id"));
                tripObject.addProperty("date_from", rs.getString("date_from"));
                tripObject.addProperty("date_to", rs.getString("date_to"));
                tripObject.addProperty("location_from", rs.getString("location_from"));
                tripObject.addProperty("location_to", rs.getString("location_to"));
                tripObject.addProperty("weather", rs.getString("weather"));

                tripsArray.add(tripObject);
            }

            response = tripsArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }

        return response;
    }
    
    public int getID(String username){
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT ID FROM USERS WHERE USERNAME = '" + username +"'";
        int response = -1;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel", "j", "j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {
                response = rs.getInt("ID");  // Ensure the case matches the column name
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public int getProposedUserID(int TripId){
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT USER_ID FROM TRIP WHERE TRIP_ID = " + TripId;
        int response = -1;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel", "j", "j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {
                response = rs.getInt("USER_ID");  // Ensure the case matches the column name
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public String getUsername(int userId){
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT USERNAME FROM USERS WHERE ID = " + userId;
        String response = "";
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel", "j", "j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {
                response = rs.getString("USERNAME");  // Ensure the case matches the column name
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public String findInterestedUsers(int userId){
        Connection mycon = null;
        Statement statement = null;
        ResultSet resultSet = null;
        JsonArray jsonArray = new JsonArray();
        String query ="SELECT t.date_from, t.date_to, t.location_from, t.location_to, i.new_user_id "
                         + "FROM trip t "
                         + "JOIN interested i ON t.trip_id = i.trip_id "
                         + "WHERE i.proposed_user_id ="+userId;
        
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel", "j", "j");
            statement = mycon.createStatement();
            resultSet = statement.executeQuery(query);
            

            while (resultSet.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("date_from", resultSet.getString("date_from"));
                jsonObject.addProperty("date_to", resultSet.getString("date_to"));
                jsonObject.addProperty("location_from", resultSet.getString("location_from"));
                jsonObject.addProperty("location_to", resultSet.getString("location_to"));
                String username = getUsername(resultSet.getInt("new_user_id"));
                jsonObject.addProperty("username",username);
                jsonArray.add(jsonObject);
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
    
    public String specifiTrip(int userId, int TripId){
        Connection mycon = null;
        Statement statement = null;
        ResultSet resultSet = null;
        JsonArray jsonArray = new JsonArray();
        String query ="SELECT t.date_from, t.date_to, t.location_from, t.location_to, i.new_user_id "
                         + "FROM trip t "
                         + "JOIN interested i ON t.trip_id = i.trip_id "
                         + "WHERE i.proposed_user_id ="+userId+ " AND t.trip_id ="+ TripId;
        
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel", "j", "j");
            statement = mycon.createStatement();
            resultSet = statement.executeQuery(query);
            

            while (resultSet.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("date_from", resultSet.getString("date_from"));
                jsonObject.addProperty("date_to", resultSet.getString("date_to"));
                jsonObject.addProperty("location_from", resultSet.getString("location_from"));
                jsonObject.addProperty("location_to", resultSet.getString("location_to"));
                String username = getUsername(resultSet.getInt("new_user_id"));
                jsonObject.addProperty("username",username);
                jsonArray.add(jsonObject);
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
    
}