/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jared
 */
public class WeatherService {
    public String getWeather(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lon", "-1.15");
        parameters.put("lat", "52.95");
        parameters.put("lang", "en");
        parameters.put("unit", "metric");
        parameters.put("output", "json");
        
        StringBuilder convertedParamsToString = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (convertedParamsToString.length() > 0) {
                convertedParamsToString.append("&");
            }
            try{
                convertedParamsToString.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            catch( Exception e){
                return e.getMessage();
            }
        }
        
        try{
            URL url = new URL("http://www.7timer.info/bin/astro.php?" + convertedParamsToString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
        
            if (con.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    con.disconnect();

                    System.out.println("Weather Forecast JSON Response:");
                    System.out.println(response.toString());
                    return "Weather Forecast JSON Response:" + response.toString();
                } else {
                    return "Failed to retrieve weather forecast. HTTP error code:" + con.getResponseCode();
                }
        }catch(Exception e){
            return e.getMessage();
        }
    }
    
    //public static void main(String args[]){
    //    WeatherService rn = new WeatherService();
    //    rn.getWeather();
    //}
}
