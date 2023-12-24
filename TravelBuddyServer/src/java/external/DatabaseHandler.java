/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;
import com.google.gson.Gson;
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
        
        return response;
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
                response = username + " " + name + " " + password;
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
        return response;
    }
    
    //public static void main(String args[]){
    //    DatabaseHandler db = new DatabaseHandler();
    //    String and = db.queryUser("stable");
    //    System.out.println(and);
    //}
}