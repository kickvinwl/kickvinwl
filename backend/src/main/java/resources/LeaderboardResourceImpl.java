package resources;

import manager.MatchdayPointsCalculater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.LeaderboardPersistenceService;
import persistence.LeaguePersistenceService;
import resources.datamodel.UserPoints;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.List;

public class LeaderboardResourceImpl extends LeaderboardResource {

    private Response response;

    @Override
    public Response getAlltimeLeaderboard() {
        response = Response.accepted().build();
        Logger slf4jLogger = LoggerFactory.getLogger("alltime board logger");
        slf4jLogger.debug("alltime board started");
        try {
            List<UserPoints> leaderboard = LeaderboardPersistenceService.getInstance().getAlltimeLeaderboard();
            response = Response.accepted(leaderboard).build();
        } catch (NoResultException e) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @Override
    public Response calculateLeaderboard() {
        MatchdayPointsCalculater.getInstance().updateUserPointsMatchday();
        return Response.accepted().build();
    }

    @Override
    public Response getSeasonLeaderboard() {
        response = Response.accepted().build();
        Logger slf4jLogger = LoggerFactory.getLogger("season board logger");
        slf4jLogger.debug("season board started");
        try {
            List<UserPoints> leaderboard = LeaderboardPersistenceService.getInstance().getSeasonLeaderboard(LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1").getId());
            response = Response.accepted(leaderboard).build();
        } catch (NoResultException e) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @Override
    public Response getGamedayLeaderboard() {
        response = Response.accepted().build();
        Logger slf4jLogger = LoggerFactory.getLogger("gameday board logger");
        slf4jLogger.debug("gameday board started");
        try {
            List<UserPoints> leaderboard = LeaderboardPersistenceService.getInstance().getGamedayLeaderboard(LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1").getCurrentMatchday().getId());
            response = Response.accepted(leaderboard).build();
        } catch (NoResultException e) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}
