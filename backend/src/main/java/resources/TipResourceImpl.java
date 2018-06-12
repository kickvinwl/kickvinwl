package resources;

import entities.User;

import persistence.MatchTipPersistenceService;

import persistence.UserPersistenceService;
import resources.datamodel.MatchTipTransform;
import resources.datamodel.Tip;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Optional;

public class TipResourceImpl extends TipResource {

//    @Override
//    public Response setTip(String token, ArrayList<Tip> tipList) {
//        response = Response.accepted().build();
//
//        try {
//            User user = UserPersistenceService.getInstance().getBySessionKey(token);
//
//            for (Tip tip : tipList) {
//                MatchTipPersistenceService.getInstance().createOrUpdateMatchTip(user, tip);
//            }
//        } catch (SecurityException | NoResultException exception) {
//            response = Response.status(Response.Status.BAD_REQUEST).build();
//        }
//
//        return response;
//    }


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
