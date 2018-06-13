package resources;

import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.MatchTipPersistenceService;
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
