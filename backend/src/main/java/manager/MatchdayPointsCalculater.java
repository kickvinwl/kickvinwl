package manager;

import entities.MatchTip;
import entities.Matchday;
import entities.User;
import entities.UserPointsMatchday;
import persistence.*;

import java.util.List;

/**
 * Kalkuliere die Punkte eines Users für einen Spieltag
 */
public class MatchdayPointsCalculater {

    private LeaderboardPersistenceService userPointsPersistenceService;
    private UserPersistenceService userPersistenceService;
    private MatchTipPersistenceService matchTipPersistenceService;
    private LeaguePersistenceService leaguePersistenceService;
    private static MatchdayPointsCalculater instance;

    public static MatchdayPointsCalculater getInstance() {
        return instance = instance != null ? instance : new MatchdayPointsCalculater();
    }

    private MatchdayPointsCalculater() {
        userPointsPersistenceService = LeaderboardPersistenceService.getInstance();
        userPersistenceService = UserPersistenceService.getInstance();
        matchTipPersistenceService = MatchTipPersistenceService.getInstance();
        leaguePersistenceService = LeaguePersistenceService.getInstance();
    }


    private List<User> getAllUser () {
        return userPersistenceService.getAll();
    }

    private Matchday getCurrentMatchday() {
        return leaguePersistenceService.getCurrentLeagueByLeagueId("bl1").getCurrentMatchday();
    }

    private int calculatePointsForUser(final int userID) {
        List<MatchTip> userTips = matchTipPersistenceService.getByUserId(userID);
        int userPoints = 0;

        for(MatchTip tip : userTips) {
            userPoints += tip.getUserPoints();
        }

        return userPoints;
    }

    public void updateUserPointsMatchday() {
        List<User> userList = getAllUser();
        Matchday matchday = getCurrentMatchday();

        for(User user : userList) {
            UserPointsMatchday userPointsMatchday= new UserPointsMatchday();
            userPointsMatchday.setMatchday(matchday);
            userPointsMatchday.setPoints(calculatePointsForUser(user.getId()));
            userPointsMatchday.setuser(user);
            userPointsPersistenceService.saveOrUpdatePoints(userPointsMatchday);
        }
    }
}
