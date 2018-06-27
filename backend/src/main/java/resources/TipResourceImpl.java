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
import java.util.Date;

public class TipResourceImpl extends TipResource {

    @Override
    public Response setTip(TipList matches) {
        response = Response.accepted().build();

        //TODO validate

        Logger slf4jLogger = LoggerFactory.getLogger("some-logger");
        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(matches.getToken());
            for (Tip tip : matches.getMatches()) {
                Match match = MatchPersistenceService.getInstance().getMatchById(tip.getmatchId());
                    createMatchTip(user, tip, match);
            }
        } catch (NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (SecurityException e) {
            response = Response.status(Response.Status.NO_CONTENT).build();
        }

        return response;
    }

    private boolean isTipValid(Tip tip)
    {
        return tip.getawayTip() != null && tip.gethomeTip() != null && tip.getawayTip() >= 0 && tip.gethomeTip() >= 0;
    }

    private boolean isMatchValid(Match match)
    {
        return match.getMatchDateTime().after(new Date());
    }

    private void createMatchTip(User user,Tip tip, Match match)
    {
        if(!isMatchValid(match) || !isTipValid(tip)){ return;}
        MatchTip ret = new MatchTip();
        try{
            ret = MatchTipPersistenceService.getInstance().getByUserIdAndMatchId(user.getId(), match.getId());
            ret.setGoalsTeam1(tip.gethomeTip());
            ret.setGoalsTeam2(tip.getawayTip());
           MatchTipPersistenceService.getInstance().update(ret);
        }catch (NoResultException e) {

            ret.setTippedMatch(match);
            ret.setOwner(user);
            ret.setGoalsTeam1(tip.gethomeTip());
            ret.setGoalsTeam2(tip.getawayTip());
            MatchTipPersistenceService.getInstance().save(ret);
        }
    }


    @Override
    public Response getTipByToken(String token, int gameday) {
        response = Response.accepted().build();
        Matchday matchdayDefault = new Matchday();//LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1").getCurrentMatchday();
        matchdayDefault.setMatchday(1);
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
