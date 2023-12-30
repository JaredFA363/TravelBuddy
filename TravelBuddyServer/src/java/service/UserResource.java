/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package service;

import external.DatabaseHandler;
import java.sql.SQLException;
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
import external.Result;
import external.ResultID;
import com.google.gson.Gson;
import javax.ws.rs.FormParam;

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
            //return client_ans;
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
    
    @Path("/getID")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getID(@QueryParam("username") String username) {
        int client_ans = -1;
        try {
            client_ans = databaseHandler.getID(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return client_ans;
        ResultID result = new ResultID(client_ans);
        Gson gson = new Gson();
        var jsonResponse = gson.toJson(result, ResultID.class);
        return jsonResponse;
    }
    
    @Path("getProposedID")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProposedID(@QueryParam("TripId") int TripId) {
        int client_ans = -1;
        try {
            client_ans = databaseHandler.getProposedUserID(TripId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return client_ans;
        ResultID result = new ResultID(client_ans);
        Gson gson = new Gson();
        var jsonResponse = gson.toJson(result, ResultID.class);
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
