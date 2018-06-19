package resources;

import entities.User;
import manager.BundesligaTableManager;
import persistence.LeaguePersistenceService;
import persistence.UserPersistenceService;
import resources.datamodel.BundesligaTableTransform;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;

public class BundesligaResourceImpl extends BundesligaResource{

    @Override
    public Response getBundesligaTable(String token) {
        response = Response.accepted().build();

        try{
            BundesligaTableManager bm = new BundesligaTableManager(LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1"));
            BundesligaTableTransform bTransform = bm.getTransform();
            if (bTransform == null) {
                response = Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                response = Response.accepted(bTransform).build();
            }
        } catch (NoResultException e) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

}
