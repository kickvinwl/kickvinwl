package resources;

import entities.UserPointsLeague;
import persistence.LeaderboardPersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.List;

public class LeaderboardResourceImpl extends LeaderboardResource{

    private Response response;

    @Override
    public Response getLeaderboardByGameday(int gameday) {
        response = Response.accepted().build();
        try {
            List<UserPointsLeague> leaderboard = LeaderboardPersistenceService.getInstance().getLeaderboard(gameday);
            response = Response.accepted(leaderboard).build();
        }
        catch (NoResultException e)
        {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}
