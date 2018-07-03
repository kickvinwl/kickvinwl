package resources;


import entities.Achievement;
import entities.User;
import persistence.UserPersistenceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:user|User}")
public abstract class UserResource {

    final static String group = "GG_APP_Ermaechtigung_GOP_Kataloge_RW";
    UserPersistenceService userPersistenceService;
    Response response;

    public UserResource() {
        userPersistenceService = UserPersistenceService.getInstance();
    }

    @POST
    @Path("/setImage/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response setImage(@PathParam("token") String token, byte[] image);

    @POST
    @Path("/setAchievment/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response setAchievment(@PathParam("token") String token, Achievement achievement);

    @GET
    @Path("/{a:get|Get}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getUserByToken(@PathParam("token") String token);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUserByName")
    public abstract Response getUserByName(@QueryParam("token") String token, @QueryParam("userName") String userName);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUserAchievementsByName")
    public abstract Response getUserAchievementsByName(@QueryParam("token") String token, @QueryParam("userName") String userName);

    @GET
    @Path("/{a:logout|Logout}")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response removeSessionKey(String token);
}
