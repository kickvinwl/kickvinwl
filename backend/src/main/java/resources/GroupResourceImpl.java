package resources;

import entities.Group;
import entities.User;
import persistence.GroupPersistenceService;
import persistence.UserPersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.Map;

public class GroupResourceImpl extends GroupResource {


    @Override
    public Response createGroup(Map<String, String> paramsamsam) {
        String token = paramsamsam.get("token");
        String groupName = paramsamsam.get("name");
        String groupPassword = paramsamsam.get("pw");

        //TODO: klaeren: sollen Gruppennamen eindeutig sein?
        // if group name already exists, negative response
        if (GroupPersistenceService.getInstance().exists(groupName)) {
            Response.ResponseBuilder rb = Response.accepted();
            rb.status(Response.Status.UNAUTHORIZED);
            return rb.build();
        }
        try {

            // user entity from db
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            // set group data
            Group newGroup = new Group();
            newGroup.setPassword(groupPassword);
            newGroup.setGroupName(groupName);
            newGroup.setAdminUser(user);
            newGroup.addUserToGroup(user);
            // persist and update
            UserPersistenceService.getInstance().update(user);
            GroupPersistenceService.getInstance().save(newGroup);
            // send positive response
            return Response.accepted().build();
        } catch (NoResultException e) {
            //TODO: Fall User nicht gefunden, eventuell korrigieren
            // send negative response
            Response.ResponseBuilder rb = Response.accepted();
            //TODO: korrekten response status setzen
            rb.status(Response.Status.NO_CONTENT);
            return rb.build();
        }
    }


    @Override
    public Response leaveGroup(String token, String groupName) {
        Response.ResponseBuilder rb = Response.accepted();
        // get user and group from db
        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            Group group = GroupPersistenceService.getInstance().getByGroupName(groupName);
            // remove from group, set next admin
            group.removeUserFromGroup(user);
            // delete or update group, update user
            if(group.usersIsEmpty()) {
                GroupPersistenceService.getInstance().delete(group);
                UserPersistenceService.getInstance().update(user);
            } else {
                GroupPersistenceService.getInstance().update(group);
                UserPersistenceService.getInstance().update(user);
                UserPersistenceService.getInstance().update(group.getAdminUser());
            }
            rb.status(Response.Status.ACCEPTED);
            return rb.build();
        } catch (NoResultException e) {
            //TODO: korrekten response status
            rb.status(Response.Status.UNAUTHORIZED);
            return rb.build();
        }

    }
}
