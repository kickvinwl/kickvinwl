package resources;

import entities.Matchday;
import entities.User;
import persistence.LeaguePersistenceService;
import persistence.MatchTipPersistenceService;
import persistence.MatchdayPersistenceService;
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
    public Response getTipByToken(String token, int gameday) {
        response = Response.accepted().build();
        MatchdayPersistenceService matchdayPersistenceService = MatchdayPersistenceService.getInstance();
        //gameday nicht gefunden in DB
        if(!matchdayPersistenceService.exists(gameday))
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            //TODO LeaguePersistenceService.getInstance().getCurrentLeague().getCurrentMatchday() einfügen unten
            Matchday matchday = (gameday == -1 ?  matchdayPersistenceService.getDefault() : new Matchday(gameday));
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            MatchTipTransform matchTip = new MatchTipTransform("2017/18", matchday, user.getTips()); //TODO Season wird noch nicht verarbeitet
            //(matchTip.getGameday().equals("0")) ?  Response.status(Response.Status.NOT_FOUND).build() :
            response = Response.accepted(matchTip).build();

        }catch (SecurityException e) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        catch (NoResultException e)
        {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }


}
