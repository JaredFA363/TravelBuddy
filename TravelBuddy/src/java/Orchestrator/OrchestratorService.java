/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Orchestrator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * REST Web Service
 *
 * @author N0992216
 */
@Path("Orchestrator")
public class OrchestratorService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of OrchestratorService
     */
    public OrchestratorService() {
    }

    /**
     * Retrieves representation of an instance of Orchestrator.OrchestratorService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        return "<html><body><h1>Hello, I am a Orchestrator service when called by a client!!</body></h1></html>";
    }
    
    /**
     * Retrieves the weather forecast in JSON format
     * @return JSON representation of the weather forecast
     */
    @Path("/weatherForecast")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getWeatherForecast() {
        
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
                    // Get the response input stream
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    // Read the response line by line
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Close the reader and connection
                    reader.close();
                    con.disconnect();

                    // Print the JSON response
                    System.out.println("Weather Forecast JSON Response:");
                    System.out.println(response.toString());
                    return "<html><body><h1>Weather Forecast JSON Response:</body></h1></html>" + response.toString();
                } else {
                    return "<html><body><h1>Failed to retrieve weather forecast. HTTP error code:</body></h1></html>" + con.getResponseCode();
                }
        
        }catch(Exception e){
            return e.getMessage();
        }
    }
    
    /**
     * Retrieves the weather forecast in JSON format
     * @return JSON representation of the weather forecast
     */
    @Path("/randomID")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getRandomID() {
        try {
            // Create the URL for the random number generation endpoint
            URL url = new URL("https://csrng.net/csrng/csrng.php?min=1&max=9999&num=" + 1);

            // Open a connection to the URL
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Get the response
            if (con.getResponseCode() == 200) {
                // Read the response input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // Read the response line by line
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Close the reader and connection
                reader.close();
                con.disconnect();

                // Print the generated IDs
                System.out.println("Generated IDs: " + response.toString());
                return response.toString();
                //return "<html><body><h1>Generated IDs:</body></h1></html>" + response.toString();
            } else {
                System.out.println("Failed to retrieve random IDs. HTTP error code: " + con.getResponseCode());
                return "<html><body><h1>Failed to retrieve random IDs. HTTP error code:</body></h1></html>" + con.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    @Path("/saveUserData")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String saveUserData(String jsonData) {
        try {
            // Parse JSON data and save it to the database using the DatabaseHandle
            //String random_number = getRandomID();
            //Gson gson = new Gson();
            //UserDataWithRandomID userDataWithRandomID = gson.fromJson(random_number, UserDataWithRandomID.class);
            //String randomID = userDataWithRandomID.storeRandomID();
            // Parse the JSON array
            //JsonArray jsonArray = new Gson().fromJson(jsonData, JsonArray.class);

            // Convert the JSON array to a JSON object
            //JsonObject jsonObject = new JsonObject();
            //jsonObject.add("users", jsonArray);
            
            DatabaseHandler databaseHandler = new DatabaseHandler();
            //databaseHandler.storeUserData(jsonData);
            String response = databaseHandler.storeUserData(jsonData);
            return "Ans" + response + " .";
            
            //return "<html><body><h1>Data saved successfully!</h1></body></html>" + jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            return "<html><body><h1>Error saving data to the database</h1></body></html>" + errors.toString();
        }
    }
    
    private static class UserDataWithRandomID {
        private String randomID;

        public String storeRandomID() {
            return randomID;
        }
    }
    
    
    
    /**
     * PUT method for updating or creating an instance of OrchestratorService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
