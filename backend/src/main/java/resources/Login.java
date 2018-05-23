package resources;

import de.kvwl.commons.authentication.AuthenticationServiceFactory;
import entities.User;
import persistence.UserPersistenceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class Login {

    final static String group = "GG_APP_Ermaechtigung_GOP_Kataloge_RW";

    @GET
    public String createToken(@QueryParam("name") String name, @QueryParam("pw") String pw) {

        //TODO eingabe prüfen

        boolean isAllow = AuthenticationServiceFactory.getInstance().isUserInGroup(name, pw, group);
        String token;

        if(isAllow)
        {
            //User finden/erstellen
            User user = null ;// = UserPersistenceService.getInstance().get(name);
            if( user == null)
            {
                //TODO User anlegen und befüllen
                user = new User();
            }

            //token generieren
            token = UUID.randomUUID().toString();
            //token setzten
            //TODO User token mit geben
            //User speichern
            UserPersistenceService.getInstance().save(user);
            Response.status(200);
            return token;
        }

        //Fehlermeldung User nicht vorhanden
        Response.status(Response.Status.NOT_ACCEPTABLE); //TODO Fehler code noch nicht abgesprochen
        return "";
    }
}