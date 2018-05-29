package resources;


import de.kvwl.commons.authentication.AuthenticationServiceFactory;
import entities.User;
import persistence.UserPersistenceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.UUID;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    final static String group = "GG_APP_Ermaechtigung_GOP_Kataloge_RW"; //TODO in die Properties

    @GET
    @Path("/{sessionKey}")
    public Response getUserBySessionKey(@PathParam("sessionKey") String sessionKey) {

        Response.ResponseBuilder rb = Response.accepted();

        //User zu sessionKey finden
        User user = UserPersistenceService.getInstance().getBySessionKey(sessionKey);

        if(user != null) {
            //Token prüfen
            //TODO prüfen ob sessionKey noch Gültig
            //TODO User.Lastlogin < AktuelleZeit + 30 min
            if (true /*Token ist Gültig*/) {
                rb.entity(user);
            } else {
                rb.status(Response.Status.UNAUTHORIZED);
            }
        }
        else
        {
            rb.status(Response.Status.BAD_REQUEST);
        }

        return rb.build();
    }
}