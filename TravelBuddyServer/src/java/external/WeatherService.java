/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jared
 */
public class WeatherService {
    public String generateWeather(String location, int numDays){
        try{
            //URL url = new URL("http://www.7timer.info/bin/civillight.php?" + convertedParamsToString);
            String apiUrl = String.format("https://api.weatherapi.com/v1/forecast.json?key=d54821ba7dec4d19a8e224016232712&q=%s&days=%d", location, numDays);
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String response = "";
            con.connect();
        
            if (con.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    
                    String line = reader.readLine();
                    while (line != null){
                        response += line + "\r\n";
                        line = reader.readLine();
                    }

                    //System.out.println("Weather Forecast JSON Response:");
                    //System.out.println(response.toString());
                    return response;
                } else {
                    return "Failed to retrieve weather forecast. HTTP error code:" + con.getResponseCode();
                }
        }catch(Exception e){
            return e.getMessage();
        }
    }
    
    public int calculateDays(String endDateString) {
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ISO_DATE);
        LocalDate currentDate = LocalDate.now();
        int daysDifference = (int) currentDate.until(endDate).getDays();
        return daysDifference;
    }
    
    public JsonObject weatherJson(String location, String startDate, String endDate) {
        
        WeatherService ws = new WeatherService();
        int numDays = ws.calculateDays(endDate) + 1;
        String JsonString = ws.generateWeather(location,numDays);
        
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(JsonString, JsonObject.class);

        JsonArray forecastdayArray = jsonObject.getAsJsonObject("forecast").getAsJsonArray("forecastday");

        Type listType = new TypeToken<List<ForecastDay>>() {}.getType();
        List<ForecastDay> forecastDays = gson.fromJson(forecastdayArray, listType);

        JsonObject filteredData = new JsonObject();
        JsonArray filteredForecastDays = new JsonArray();

        for (ForecastDay forecastDay : forecastDays) {
            if (forecastDay.date.compareTo(startDate) >= 0 && forecastDay.date.compareTo(endDate) <= 0) {
                JsonObject dayObject = new JsonObject();
                dayObject.addProperty("date", forecastDay.date);
                dayObject.addProperty("avgtemp_c", forecastDay.day.avgtemp_c);
                dayObject.addProperty("condition", forecastDay.day.condition.text);
                filteredForecastDays.add(dayObject);
            }
        }

        filteredData.add("filteredForecastDays", filteredForecastDays);

        return filteredData;
    }
    
    /*public static void main(String[] args) {
        WeatherService ws = new WeatherService();
        System.out.println(ws.weatherJson("london","2023-12-30","2023-12-31"));
    }*/
}
