package resources;


import entities.User;
import persistence.UserPersistenceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:user|User}")
public abstract class UserResource {

    final static String group = "GG_APP_Ermaechtigung_GOP_Kataloge_RW";
    UserPersistenceService userPersistenceService;

    public UserResource() {
        //TODO: User Persistence Service Instanziieren;
        userPersistenceService = new UserPersistenceService();
    }

    @GET
    @Path("/{a:get|Get}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getUser(@PathParam("token") String token);


    @POST
    @Path("a:set|Set")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response setUser(User user);


    @GET
    @Path("/{sessionKey}")
    public abstract Response getUserBySessionKey(@PathParam("sessionKey") String sessionKey);

    @POST
    @Path("/{a:logout|Logout}")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response removeSessionKey(String token);
}
