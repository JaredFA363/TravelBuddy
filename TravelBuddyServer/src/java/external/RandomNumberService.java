/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author jared
 */
public class RandomNumberService {
    public String getRandomID(){
        try {
            URL url = new URL("https://csrng.net/csrng/csrng.php?min=1&max=9999&num=" + 1);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                con.disconnect();

                System.out.println("Generated IDs: " + response.toString());
                return response.toString();
            } else {
                System.out.println("Failed to retrieve random IDs. HTTP error code: " + con.getResponseCode());
                return "Failed to retrieve random IDs. HTTP error code:" + con.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    //public static void main(String args[]){
    //    RandomNumberService rn = new RandomNumberService();
    //    rn.getRandomID();
    //}
}
