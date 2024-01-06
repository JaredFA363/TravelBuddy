/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package service;

import com.google.gson.Gson;
import external.DatabaseHandler;
import external.Result;
import external.WeatherService;
import external.checkWeatherData;
import external.getIDdata;
import external.getQueryData;
import external.getSpecificTrip;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author jared
 */
@Path("Trip")
public class TripResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TripResource
     */
    public TripResource() {
    }
    
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    @Path("/insertTrip")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertTrip(String JsonData){
        String client_ans = "Error - Resource";
        try{
            client_ans = databaseHandler.insertTrip(JsonData);
        }
        catch(Exception s){
            s.printStackTrace();
            //client_ans = s.getMessage() + JsonData;
        }
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            Gson gson = new Gson();
            var jsonResponse = gson.toJson(result, Result.class);
            return jsonResponse;
        }
        return client_ans;
    }
    
    @Path("/queryTrip")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String queryTrip(String JsonData){
        Gson gson = new Gson();
        getQueryData data = gson.fromJson(JsonData, getQueryData.class);
        
        String location_from = data.getLocation_from();
        String location_to = data.getLocation_to();
        String client_ans = "Error - Resource";
        try{
            client_ans = databaseHandler.queryTrips(location_from,location_to);
        }
        catch(Exception s){
            s.printStackTrace();
            //client_ans = s.getMessage();
        }
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            var jsonResponse = gson.toJson(result, Result.class);
            return jsonResponse;
        }
        
        return client_ans;
    }
    
    @Path("/expressInterest")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String expressInterest(String JsonData){
        String client_ans = "Error - Resource";
        try{
            client_ans = databaseHandler.expressInterest(JsonData);
        }
        catch(Exception s){
            s.printStackTrace();
            //client_ans = s.getMessage() + JsonData;
        }
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            Gson gson = new Gson();
            var jsonResponse = gson.toJson(result, Result.class);
            return jsonResponse;
        }
        return client_ans;
    }
    
    @Path("/CheckWeather")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String CheckWeather(String JsonData){
        Gson gson = new Gson();
        checkWeatherData data = gson.fromJson(JsonData, checkWeatherData.class);
        String date_from = data.getDate_from();
        String date_to = data.getDate_to();
        String location_to = data.getLocation_to();
        
        WeatherService ws = new WeatherService();
        return ws.weatherJson(location_to,date_from,date_to).toString();
    }
    
    @Path("/getAllTrips")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTrips() {
        String client_ans = "Error - Resource";
        try {
            client_ans = databaseHandler.getAllTrips();
        } catch (Exception e) {
            e.printStackTrace();
            //client_ans = e.getMessage();
        }
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            Gson gson = new Gson();
            var jsonResponse = gson.toJson(result, Result.class);
            return jsonResponse;
        }
        return client_ans;
    }
    
    @Path("/getyourTrips")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getyourTrips(String JsonData) {
        String client_ans = "Error - Resource";
        int userID = unmarshallID(JsonData);
        try {
            client_ans = databaseHandler.findInterestedUsers(userID);
        } catch (Exception e) {
            e.printStackTrace();
            //client_ans = e.getMessage();
        }
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            Gson gson = new Gson();
            var jsonResponse = gson.toJson(result, Result.class);
            return jsonResponse;
        }
        return client_ans;
    }
    
    public int unmarshallID(String JsonData){
        Gson gson = new Gson();
        getIDdata IDdata = gson.fromJson(JsonData, getIDdata.class);
        return IDdata.getUserId();
    }
    
    @Path("/getSpecificTrips")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getSpecificTrips(String JsonData) {
        String client_ans = "Error - Resource";
        
        Gson gson = new Gson();
        getSpecificTrip data = gson.fromJson(JsonData, getSpecificTrip.class);
        
        int user_id = Integer.parseInt(data.getUserId());
        int trip_id = Integer.parseInt(data.getTripId());
        
        try {
            client_ans = databaseHandler.specifiTrip(user_id, trip_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            var jsonResponse = gson.toJson(result, Result.class);
            return jsonResponse;
        }
        return client_ans;
    }
    
    /**
     * Retrieves representation of an instance of service.TripResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of TripResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
}
