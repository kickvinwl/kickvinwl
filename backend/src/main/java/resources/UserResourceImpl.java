package resources;

import entities.User;

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
        return null;
    }
}
