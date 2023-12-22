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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jared
 */
public class WeatherService {
    public String generateWeather(){
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
            URL url = new URL("http://www.7timer.info/bin/civillight.php?" + convertedParamsToString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String response = "";
            con.connect();
        
            if (con.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //StringBuilder response = new StringBuilder();
                    //String line;

                    //while ((line = reader.readLine()) != null) {
                    //    response.append(line);
                    //}
                    //reader.close();
                    //con.disconnect();
                    
                    String line = reader.readLine();
                    while (line != null){
                        response += line + "\r\n";
                        line = reader.readLine();
                    }

                    System.out.println("Weather Forecast JSON Response:");
                    System.out.println(response.toString());
                    //return "Weather Forecast JSON Response:" + response.toString();
                    return response;
                } else {
                    return "Failed to retrieve weather forecast. HTTP error code:" + con.getResponseCode();
                }
        }catch(Exception e){
            return e.getMessage();
        }
    }
    
    public WeatherGetterSetter getWeather(){
        String weatherJSON = this.generateWeather();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        WeatherGetterSetter ws = gson.fromJson(weatherJSON, WeatherGetterSetter.class);
        
        return ws;
    }
    
    /*public static void main(String args[]){
        WeatherService ws = new WeatherService();
        ws.generateWeather();
        WeatherGetterSetter wgs = ws.getWeather();

        List<WeatherGetterSetter.dataseries> dataseriesList = wgs.getDataseries();
        if (dataseriesList != null) {
            for (WeatherGetterSetter.dataseries dataseries : dataseriesList) {
                System.out.println("Date: " + dataseries.getDate());
                System.out.println("Weather: " + dataseries.getWeather());

                WeatherGetterSetter.dataseries.Temp2m temp2m = dataseries.getTemp2m();
                if (temp2m != null) {
                    System.out.println("Max Temperature: " + temp2m.getMax());
                    System.out.println("Min Temperature: " + temp2m.getMin());
                }
                System.out.println();
            }
        }
    }*/
}
