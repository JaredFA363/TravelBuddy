/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author jared
 */
public class RandomNumberService {
    public String generateRandomID(){
        String response = "";
        try {
            URL url = new URL("https://csrng.net/csrng/csrng.php?min=1&max=9999&num=" + 1);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type","application/json;charset=utf-8");
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
                String line = reader.readLine();
                while (line != null){
                    response += line + "\r\n";
                    line = reader.readLine();
                }
                response = response.substring(1, response.length()-3);
                //System.out.println("Generated IDs: " + response.toString());
            } else {
                System.out.println("Failed to retrieve random IDs. HTTP error code: " + con.getResponseCode());
                return "Failed to retrieve random IDs. HTTP error code:" + con.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public RandomNumGetterSetter getRandomID(){
        String ID = this.generateRandomID();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        RandomNumGetterSetter rn = gson.fromJson(ID, RandomNumGetterSetter.class);
        
        return rn;
    }
    
}
