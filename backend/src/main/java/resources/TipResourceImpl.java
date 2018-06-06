package resources;

import entities.Match;
import entities.MatchTip;
import entities.Team;
import entities.User;
import persistence.MatchTipPersistenceService;
import persistence.UserPersistenceService;
import resources.datamodel.MatchTipTransform;

import javax.ws.rs.core.Response;
import java.util.List;

public class TipResourceImpl extends TipResource {

    @Override
    public Response setTip(String token) {
        Response.ResponseBuilder response = Response.accepted();

        //Token -> User

        //


        return response.build();
    }



    @Override
    public Response getTipByToken(String gameday, String token) {
        Response.ResponseBuilder response = Response.accepted();

        //Token -> User
        User user = UserPersistenceService.getInstance().getBySessionKey(token);

        //MatchTipTransform füllen
        MatchTipTransform matchTip = new MatchTipTransform();
        matchTip.setGameday(gameday);
        matchTip.setSeason("2017/18"); //TODO Season besorgen

        MatchTipTransform.MatchWithPoints matche;

        return response.entity(matchTip).build();
    }

    private MatchTipTransform.MatchWithPoints createMatch()
    {
        MatchTipTransform.MatchWithPoints ret = new MatchTipTransform.MatchWithPoints();



        return ret;
    }

    private MatchTipTransform.MatchWithPoints.Team creatTeam(MatchTip matchTip, Team team, Match match, boolean isHomeTeam)
    {
        MatchTipTransform.MatchWithPoints.Team ret = new MatchTipTransform.MatchWithPoints.Team();

        ret.setId(team.getId());
        ret.setName(team.getTeamName());
        ret.setLogo(team.getTeamIconURL());
        ret.setTipScore((isHomeTeam) ? matchTip.getGoalsTeam1() : matchTip.getGoalsTeam2());
        ret.setRealScore((isHomeTeam) ? match.getGoalsTeam1() : match.getGoalsTeam2());

        return ret;
    }
}
