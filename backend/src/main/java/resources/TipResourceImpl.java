package resources;

import entities.Matchday;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Response setTip(TipList matches) {
        response = Response.accepted().build();
        Logger slf4jLogger = LoggerFactory.getLogger("some-logger");
        slf4jLogger.info("An info log message2");
        try {
            slf4jLogger.info("An info log message3");
            User user = UserPersistenceService.getInstance().getBySessionKey(matches.getToken());
            slf4jLogger.info("An info log message4");

            for (Tip tip : matches.getMatches()) {
                slf4jLogger.info("An info log message5");
                MatchTipPersistenceService.getInstance().createOrUpdateMatchTip(user, tip);
            }
        } catch (NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        catch (SecurityException e) {
            response = Response.status(Response.Status.NO_CONTENT).build();
        }

        return response;
    }


    @Override
    public Response getTipByToken(String token, int gameday) {
        response = Response.accepted().build();
        MatchdayPersistenceService matchdayPersistenceService = MatchdayPersistenceService.getInstance();
        //gameday nicht gefunden in DB
        try {
            //TODO LeaguePersistenceService.getInstance().getCurrentLeague().getCurrentMatchday() einfügen unten
            Matchday matchday = (gameday == -1 ?  matchdayPersistenceService.getDefault() : new Matchday(gameday));
            if(!matchdayPersistenceService.exists(matchday.getMatchday()))
            {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
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
