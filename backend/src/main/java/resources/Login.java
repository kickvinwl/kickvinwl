package resources;


import de.kvwl.commons.authentication.AuthenticationServiceFactory;
import entities.User;
import persistence.UserPersistenceService;


import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


@Produces(MediaType.APPLICATION_JSON)
@Path("/login")
public class Login {


    @GET
    public Response createToken(@QueryParam("name") String name, @QueryParam("pw") String pw, @DefaultValue("GG_APP_Ermaechtigung_GOP_Kataloge_RW") @QueryParam("group") String group) {

        Response.ResponseBuilder rb = Response.accepted();

        boolean isAllow = AuthenticationServiceFactory.getInstance().isUserInGroup(name, pw, group);
        HashMap hmap = new HashMap<String, String>();

        if(isAllow)
        {
            try {

                //User finden/erstellen
                User user = UserPersistenceService.getInstance().getByName(name);

                //token generieren

                //token setzten
                user.setSessionKey(generateToken());
                hmap.put("token", user.getSessionKey());
                rb.entity(hmap);
                    setSettionTime(user);

                    //User speichern
                    UserPersistenceService.getInstance().update(user);

            }
            catch (NoResultException re)
            {
                rb.status(Response.Status.NO_CONTENT); //TODO hier nutzer anlegen
                System.out.println("Neuer Nutzer angelegt:");
                hmap.put("token", createNewUser(name));
                rb.entity(hmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } else {
            rb.status(Response.Status.UNAUTHORIZED);
        }

        return rb.build();
    }

    private void setSettionTime(User u)
    {
        u.setLastAction(new Date());
    }

    /**
     *
     * @param userName
     * @return Token
     */
    private String createNewUser(String userName)
    {
        User user = new User();
        user.setUserName(userName);
        user.setUserPicture("default");
        user.setUserIsAdmin(userName == "Woelk_m");
        user.setSessionKey(generateToken());
        setSettionTime(user);

        UserPersistenceService.getInstance().save(user);

        return user.getSessionKey();
    }

    @Deprecated
    @GET
    @Path("/logout/{sessionKey}")
    public Response getUserBySessionKey(@PathParam("sessionKey") String sessionKey) {

        Response.ResponseBuilder rb = Response.accepted();

        //User zu sessionKey finden
        User user = UserPersistenceService.getInstance().getBySessionKey(sessionKey);

        if(user != null) {
            user.setSessionKey("");
            UserPersistenceService.getInstance().update(user);
        }
        else
        {
            rb.status(Response.Status.UNAUTHORIZED);
        }

        return rb.build();
    }

    private String generateToken()
    {
        return UUID.randomUUID().toString();
    }
}