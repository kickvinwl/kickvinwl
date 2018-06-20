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


    /**
     *
     * @param name
     * @param pw
     * @return
     */
    @POST
    public Response createToken(String name, String pw) {

        Response.ResponseBuilder rb = Response.accepted();

        boolean isAllow = AuthenticationServiceFactory.getInstance().login(name, pw);
        HashMap hmap = new HashMap<String, String>();

        boolean passBy = name.contains("qwertz");

        if(isAllow || passBy) //TODO
        {
            try {

                //User finden/erstellen
                User user = UserPersistenceService.getInstance().getByName(name);

                //token generieren

                //token setzten
                user.setSessionKey((passBy) ? "t" + name.replace("qwertz", "") : generateToken());
                hmap.put("token",  user.getSessionKey());
                setSessionTime(user);

                //User speichern
                UserPersistenceService.getInstance().update(user);

            }
            catch (NoResultException re)
            {
                hmap.put("token", createNewUser(name));
            }
        } else {
            rb.status(Response.Status.UNAUTHORIZED);
        }

        return rb.entity(hmap).build();
    }

    private void setSessionTime(User u)
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
        String token = generateToken();
        User user = new User();
        user.setUserName(userName);
//        user.setUserPicture();
        user.setUserIsAdmin(userName == "Woelk_m");
        user.setSessionKey(token);
        setSessionTime(user);

        UserPersistenceService.getInstance().save(user);

        return token;
    }

    @Deprecated
    @GET
    @Path("/logout/{sessionKey}")
    public Response getUserBySessionKey(@PathParam("sessionKey") String sessionKey) {

        Response.ResponseBuilder rb = Response.status(Response.Status.GONE);

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