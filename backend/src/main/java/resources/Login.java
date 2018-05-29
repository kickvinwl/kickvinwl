package resources;


import de.kvwl.commons.authentication.AuthenticationServiceFactory;
import entities.User;
import persistence.UserPersistenceService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.UUID;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class Login {

    final static String group = "GG_APP_Ermaechtigung_GOP_Kataloge_RW"; //TODO in die Properties

    @GET
    public Response createToken(@QueryParam("name") String name, @QueryParam("pw") String pw) {

        Response.ResponseBuilder rb = Response.accepted();

        boolean isAllow = AuthenticationServiceFactory.getInstance().isUserInGroup(name, pw, group);
        String token;

        if(isAllow)
        {
            //User finden/erstellen
            User user = UserPersistenceService.getInstance().getByName(name);
            if( user == null)
            {
                rb.status(Response.Status.NO_CONTENT);
            }
            else {
                //token generieren
                token = generateToken();
                //token setzten
                user.setSessionKey(token);
                //TODO Last login setzten für User
                //User speichern
                UserPersistenceService.getInstance().update(user);

                UserPersistenceService.getInstance().update(user);
                HashMap hmap = new HashMap<String, String>();
                hmap.put("token", token);
                rb.entity(hmap);
            }
        } else {
            rb.status(Response.Status.UNAUTHORIZED);
        }

        return rb.build();
    }

    private String generateToken()
    {
        return UUID.randomUUID().toString();
    }
}