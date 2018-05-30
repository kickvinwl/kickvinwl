package resources;

import entities.User;
import persistence.UserPersistenceService;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class UserResourceImpl extends UserResource {

    @Override
    public User getUser(String userName) {
        // return userPersistenceService.get(userName);

        User user = new User();
        user.setUserName("Fritz");
        user.setUserIsAdmin(true);
        return user;
    }

    @Override
    public Response setUser(User user) {
        userPersistenceService.update(user);
        return Response.accepted().build();
    }

    @Override
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
