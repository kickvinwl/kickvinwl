package resources;

import entities.User;
import persistence.MatchTipPersistenceService;
import persistence.UserPersistenceService;
import resources.datamodel.MatchTipTransform;
import resources.datamodel.Tip;
import resources.datamodel.TipList;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;

public class TipResourceImpl extends TipResource {

    @Override
    public Response setTip(TipList tips) {
        response = Response.accepted().build();

        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(tips.getToken());

            for (Tip tip : tips.getTips()) {
                MatchTipPersistenceService.getInstance().createOrUpdateMatchTip(user, tip);
            }
        } catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }

        return response;
    }


    @Override
    public Response getTipByToken(String token, String gameday) {
        response = Response.accepted().build();

        try {

            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            MatchTipTransform matchTip = new MatchTipTransform("2017/18", gameday, user.getTips());
            response = Response.accepted(matchTip).build();

        }catch (SecurityException | NoResultException exeption) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }


}
