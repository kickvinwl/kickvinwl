package resources;

import entities.Group;
import entities.User;
import persistence.GroupPersistenceService;
import persistence.UserPersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;

public class GroupResourceImpl extends GroupResource{


    @Override
    public Response createGroup(String token, String groupName, String groupPassword) {
        try {
            // user entity from db
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            // set group data
            Group newGroup = new Group();
            newGroup.setPassword(groupPassword);
            newGroup.setGroupName(groupName);
            newGroup.setAdminUserId(user);
            newGroup.addUserToGroup(user);
            // persist and update
            UserPersistenceService.getInstance().update(user);
            GroupPersistenceService.getInstance().save(newGroup);
            // send positive response
            return Response.accepted().build();
        } catch ( NoResultException e ) {
            //TODO: Fall User nicht gefunden, eventuell korrigieren
            // send negative response
            Response.ResponseBuilder rb = Response.accepted();
            rb.status(Response.Status.BAD_REQUEST);
            return rb.build();
        }
    }
}
