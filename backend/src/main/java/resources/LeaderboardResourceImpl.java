package resources;

import entities.League;
import entities.UserPointsMatchday;
import persistence.LeaderboardPersistenceService;
import persistence.LeaguePersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.List;

public class LeaderboardResourceImpl extends LeaderboardResource{

    private Response response;

    @Override
    public Response getAlltimeLeaderboard() {
        response = Response.accepted().build();
        try {
            List<UserPointsMatchday> leaderboard = LeaderboardPersistenceService.getInstance().getgetAlltimeLeaderboard();
            response = Response.accepted(leaderboard).build();
        }
        catch (NoResultException e)
        {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @Override
    public Response getSeasonLeaderboard() {
        return null;
    }

    @Override
    public Response getGamedayLeaderboard() {
        response = Response.accepted().build();
        League currentLeague = LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1");
        try {
            List<UserPointsMatchday> leaderboard = LeaderboardPersistenceService.getInstance().getGamedayLeaderboard(currentLeague.getCurrentMatchday().getId());
            response = Response.accepted(leaderboard).build();
        }
        catch (NoResultException e)
        {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}
