/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Orchestrator;
import java.sql.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author jared
 */
public class DatabaseHandler {

    public String storeUserData(String jsonData){
        try{
            Class.forName("org.sqlite.JDBC");
        }
        catch(Exception e){
            System.out.println("Exception 1:" + e);
            return "Exception 1:" + e;
        }
        
        
        Connection connect;
        Statement statement;

        try{
            connect = DriverManager.getConnection("jdbc:sqlite:TravelBuddyDB.db");
            statement = connect.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Users(id integer primary key, username string, name string, password string)";
            statement.executeUpdate(createTableSQL);
        }
        catch(SQLException s){
            System.out.println("Exception 2: " + s);
            return "Exception 2:" + s;
        }

        String jsonString =  jsonData;
        String username = parseUsername(jsonString);
        String name = parseName(jsonString);
        String password = parsePassword(jsonString);
        //int RandomId = Integer.parseInt(randomId);
        String sql = "INSERT INTO Users (id, username, name, password) VALUES (10, '" + username + "', '" + name + "', '" + password + "')";

        try{
            statement = connect.createStatement();
            statement.executeUpdate(sql);
            connect.close();
            return "Success";
        }
        catch (SQLException s){
            System.out.println("Exception 3: " + s);
            return "Exception 3:" + s;
        }
    }

    Gson gson = new Gson();
    private String parseUsername(String jsonData) {
        try {
            UserData userData = gson.fromJson(jsonData, UserData.class);
            return userData != null ? userData.getUsername() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseName(String jsonData) {
        try {
            UserData userData = gson.fromJson(jsonData, UserData.class);
            return userData != null ? userData.getName() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parsePassword(String jsonData) {
        try {
            UserData userData = gson.fromJson(jsonData, UserData.class);
            return userData != null ? userData.getPassword() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    

    public static class UserData {
        private String username;
        private String name;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
    }
}
