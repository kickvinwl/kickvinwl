package resources;


import de.kvwl.commons.authentication.AuthenticationServiceFactory;
import entities.User;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.UUID;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class Login {

    final static String group = "GG_APP_Ermaechtigung_GOP_Kataloge_RW";

    @GET
    public HashMap createToken(@QueryParam("name") String name, @QueryParam("pw") String pw) {

        //TODO eingabe prüfen

        boolean isAllow = AuthenticationServiceFactory.getInstance().isUserInGroup(name, pw, group);
        String token;

        if(isAllow)
        {
            //User finden/erstellen
            User user = null ;//TODO Wirft Fehler! noch nicht fertig? -->// = UserPersistenceService.getInstance().get(name);
            if( user == null)
            {
                //TODO User anlegen und befüllen
                user = new User();
            }

            //token generieren
            token = generateToken();
            //token setzten
            //TODO User token mit geben
            //User speichern

            //TODO Wirft Fehler! noch nicht fertig? -->//UserPersistenceService.getInstance().save(user);
            Response.status(200);
            HashMap hmap = new HashMap<String, String>();
            hmap.put("token",token);
            return hmap;
        }

        //Fehlermeldung User nicht vorhanden
        Response.status(Response.Status.UNAUTHORIZED).build();
        return null;
    }

    private String generateToken()
    {
        return UUID.randomUUID().toString();
    }
}