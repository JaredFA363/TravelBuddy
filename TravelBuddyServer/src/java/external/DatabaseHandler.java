/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;
import java.sql.*;

/**
 *
 * @author jared
 */
public class DatabaseHandler {
    
    public String insertUser(){
        Connection mycon = null;
        Statement statement = null;
        String query = "INSERT INTO USERS (id, username, name, password) VALUES (10,'y','y','y')";
        String response = "Error"; 
        
        try{
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            statement.executeUpdate(query);
            response = "Success";
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return response;
    }
    
    public String queryUser(){
        Connection mycon = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM users WHERE username = 'u'";
        String response = "Error"; 
        
        try{
            mycon = DriverManager.getConnection("jdbc:derby://localhost:1527/Travel","j","j");
            statement = mycon.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String password = rs.getString("password");
                System.out.println(id + " " + username + " " + name + " " + password);
                response = "Success";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return response;
    }
}