package resources;


import entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:user|User}")
public abstract class UserResource {

    public UserResource() {
        //TODO: User Persistence Service Instanziieren;
    }

    @GET
    @Path("/{a:get|Get}/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public  abstract User getUser(@PathParam("userName") String userName);


    @POST
    @Path("a:set|Set")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response setUser(User user);



}
