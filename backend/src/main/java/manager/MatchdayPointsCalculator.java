package manager;

import entities.MatchTip;
import entities.Matchday;
import entities.User;
import entities.UserPointsMatchday;
import persistence.*;

import java.util.List;

public class MatchdayPointsCalculator {
    private LeaderboardPersistenceService userPointsPersistenceService;
    private UserPersistenceService userPersistenceService;
    private MatchTipPersistenceService matchTipPersistenceService;
    private LeaguePersistenceService leaguePersistenceService;
    private static MatchdayPointsCalculator instance;

    public static MatchdayPointsCalculator getInstance() {
        return instance = instance != null ? instance : new MatchdayPointsCalculator();
    }

    private MatchdayPointsCalculator() {
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
        userList.forEach(this::initializeUserPointsMatchday);
    }

    private void initializeUserPointsMatchday(User user) {
        Matchday matchday = getCurrentMatchday();
        UserPointsMatchday userPointsMatchday= new UserPointsMatchday();
        userPointsMatchday.setMatchday(matchday);
        userPointsMatchday.setPoints(calculatePointsForUser(user.getId()));
        userPointsMatchday.setuser(user);
        userPointsPersistenceService.saveOrUpdatePoints(userPointsMatchday);
    }





}
