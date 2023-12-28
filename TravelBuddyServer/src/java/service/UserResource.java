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
        String client_ans = "Error";
        try{
            client_ans = databaseHandler.insertUser(JsonData);
            //return client_ans;
        }
        catch(Exception s){
            s.printStackTrace();
            client_ans = s.getMessage() + JsonData;
        }
        
        Result result = new Result(client_ans);
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(result, Result.class);
        
        return jsonResponse;
        //return client_ans;
    }
    
    @Path("/queryUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String queryUser(@QueryParam("username") String username, @QueryParam("password") String password){
        String client_ans = "";
        try{
            client_ans = databaseHandler.queryUser(username,password);
        }
        catch(Exception s){
            s.printStackTrace();
            client_ans = s.getMessage();
        }
        
        Result result = new Result(client_ans);
        Gson gson = new Gson();
        var jsonResponse = gson.toJson(result, Result.class);
        
        return jsonResponse;
        //return client_ans;
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
