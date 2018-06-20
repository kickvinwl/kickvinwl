package manager;

import entities.MatchTip;
import entities.Matchday;
import entities.User;
import entities.UserPointsMatchday;
import persistence.LeaguePersistenceService;
import persistence.MatchTipPersistenceService;
import persistence.UserPersistenceService;
import persistence.UserPointsPersistenceService;

import java.util.List;

public class MatchdayPointsCalculater {
    private UserPointsPersistenceService userPointsPersistenceService;
    private UserPersistenceService userPersistenceService;
    private MatchTipPersistenceService matchTipPersistenceService;
    private LeaguePersistenceService leaguePersistenceService;
    private static MatchdayPointsCalculater instance = new MatchdayPointsCalculater();

    public static MatchdayPointsCalculater getInstance() {
        return instance;
    }

    private MatchdayPointsCalculater() {
        userPointsPersistenceService = UserPointsPersistenceService.getInstance();
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

    public void updateUserPointsMatchday(String leagueID) {
        List<User> userList = getAllUser();
        Matchday matchday = getCurrentMatchday();

        for(User user : userList) {
            UserPointsMatchday userPointsMatchday= new UserPointsMatchday();
            userPointsMatchday.setMatchday(matchday);
            userPointsMatchday.setPoints(calculatePointsForUser(user.getId()));
            userPointsMatchday.setuser(user);
            userPointsPersistenceService.save(userPointsMatchday);
        }
    }





}
