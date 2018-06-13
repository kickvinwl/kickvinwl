package resources;

import entities.User;
import persistence.LeaguePersistenceService;
import persistence.UserPersistenceService;
import resources.datamodel.BundesligaTableTransform;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;

public class BundesligaResourceImpl extends BundesligaResource{

    @Override
    public Response getBundesligaTable(String token) {
        response = Response.accepted().build();

        try {
            //User user = UserPersistenceService.getInstance().getBySessionKey(token);
            BundesligaTableTransform bundesligaTableTransform = new BundesligaTableTransform(LeaguePersistenceService.getInstance().getCurrentLeague().getId());
            response = Response.accepted(bundesligaTableTransform).build();
        } catch (NoResultException e) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }

        return response;
    }

}
