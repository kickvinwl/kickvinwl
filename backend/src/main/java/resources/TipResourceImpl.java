package resources;

import entities.Match;
import entities.MatchTip;
import entities.Matchday;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.*;
import resources.datamodel.MatchTipTransform;
import resources.datamodel.Tip;
import resources.datamodel.TipList;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;

public class TipResourceImpl extends TipResource {

    @Override
    public Response setTip(TipList matches) {
        System.out.println("#######-------------- 1");
        response = Response.accepted().build();

        //TODO validate

        Logger slf4jLogger = LoggerFactory.getLogger("some-logger");
        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(matches.getToken());
            for (Tip tip : matches.getMatches()) {
                createMatchTip(user, tip);
            }
        } catch (NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (SecurityException e) {
            response = Response.status(Response.Status.NO_CONTENT).build();
        }

        return response;
    }

    private MatchTip createMatchTip(User user,Tip tip)
    {
        MatchTip ret = new MatchTip();
        Match match = MatchPersistenceService.getInstance().getMatchById(tip.getmatchId());
        System.out.println(match);

        try{
            ret = MatchTipPersistenceService.getInstance().getByUserIdAndMatchId(user.getId(), match.getMatchID());
            MatchTipPersistenceService.getInstance().update(ret);
        }catch (NoResultException e) {

            ret.setTippedMatch(match);

            ret.setOwner(user);
            ret.setGoalsTeam1(tip.gethomeTip());
            ret.setGoalsTeam2(tip.getawayTip());
            MatchTipPersistenceService.getInstance().save(ret);
        }

        return ret;
    }


    @Override
    public Response getTipByToken(String token, int gameday) {
        response = Response.accepted().build();
        System.out.println("Strat get tip" + gameday);
        Matchday matchdayDefault = new Matchday();//LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1").getCurrentMatchday();
//        matchdayDefault.setMatchday(27);
//        matchdayDefault.setId(18);

        MatchdayPersistenceService matchdayPersistenceService = MatchdayPersistenceService.getInstance();
        if(gameday == -1) gameday = matchdayDefault.getMatchday();
        try {
            Matchday matchday;
            try {
                matchday = matchdayPersistenceService.getMatchdayBeiInt(gameday); //TODO wenn matchday nicht vorhanden -> wird zu default matchday
            } catch (NoResultException e) {
                throw new NoResultException("Matchday mit dem Tag " + gameday + " wurde nicht gefunden!");
            }
            System.out.println("#################" +matchdayDefault.getMatchday());
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            MatchTipTransform matchTip = new MatchTipTransform("2017/18", matchday, user); //TODO Season wird noch nicht verarbeitet
            //(matchTip.getGameday().equals("0")) ?  Response.status(Response.Status.NOT_FOUND).build() :
            response = Response.accepted(matchTip).build();

        } catch (SecurityException e) {
            e.printStackTrace();
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (NoResultException e) {
            e.printStackTrace();
            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        return response;
    }


}
