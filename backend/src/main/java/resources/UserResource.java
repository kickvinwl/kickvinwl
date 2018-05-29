package resources;


import entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("a:user|User")
public abstract class UserResource {


    @GET
    @Path("a:get|Get")
    @Produces(MediaType.APPLICATION_JSON)
    public  abstract User getUser();


    @POST
    @Path("a:set|Set")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response setUser();



}
