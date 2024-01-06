/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package service;

import external.DatabaseHandler;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import external.Result;
import external.ResultID;
import com.google.gson.Gson;

/**
 * REST Web Service
 *
 * @author jared
 */
@Path("User")
public class UserResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }
    
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    @Path("/insertUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertUser(String JsonData){
        String client_ans = "Error - Resource";
        try{
            client_ans = databaseHandler.insertUser(JsonData);
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
    
    @Path("/queryUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryUser(String JsonData){
        String client_ans = "Error - Resource";
        try{
            client_ans = databaseHandler.queryUser(JsonData);
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
    
    @Path("/getID")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getID(String JsonData) {
        Gson gson = new Gson();
        Result result = gson.fromJson(JsonData, Result.class);
        String username = result.getKey();
        
        int client_ans = -1;
        try {
            client_ans = databaseHandler.getID(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultID results = new ResultID(client_ans);
        var jsonResponse = gson.toJson(results, ResultID.class);
        return jsonResponse;
    }
    
    @Path("getProposedID")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getProposedID(String JsonData) {
        Gson gson = new Gson();
        Result result = gson.fromJson(JsonData, Result.class);
        int TripId = Integer.parseInt(result.getKey());
        int client_ans = -1;
        try {
            client_ans = databaseHandler.getProposedUserID(TripId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultID results = new ResultID(client_ans);
        var jsonResponse = gson.toJson(results, ResultID.class);
        return jsonResponse;
    }
    
    /**
     * Retrieves representation of an instance of service.UserResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
