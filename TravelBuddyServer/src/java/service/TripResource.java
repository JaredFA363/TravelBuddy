/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import external.DatabaseHandler;
import external.Result;
import external.WeatherService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
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
            client_ans = s.getMessage() + JsonData;
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String queryTrip(@QueryParam("location_from") String location_from, @QueryParam("location_to") String location_to){
        String client_ans = "Error - Resource";
        try{
            client_ans = databaseHandler.queryTrips(location_from,location_to);
        }
        catch(Exception s){
            s.printStackTrace();
            client_ans = s.getMessage();
        }
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            Gson gson = new Gson();
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
            client_ans = s.getMessage() + JsonData;
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String CheckWeather(@QueryParam("location_to") String location_to, @QueryParam("date_from") String date_from, @QueryParam("date_to") String date_to){
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
            client_ans = e.getMessage();
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getyourTrips(@QueryParam("userId") int userId) {
        String client_ans = "Error - Resource";
        try {
            client_ans = databaseHandler.findInterestedUsers(userId);
        } catch (Exception e) {
            e.printStackTrace();
            client_ans = e.getMessage();
        }
        if (client_ans.equals("Error - Resource")){
            Result result = new Result(client_ans);
            Gson gson = new Gson();
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
