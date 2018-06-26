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
    private static MatchdayPointsCalculator INSTANCE;
    private static Matchday MATCHDAY;
    private List<User> userList;

    public static MatchdayPointsCalculator getInstance() {
        return INSTANCE = INSTANCE != null ? INSTANCE : new MatchdayPointsCalculator();
    }

    public void updateUserPointsMatchday() {
         userList.forEach(this::initializeUserPointsMatchday);
    }

    public void calculateUserPointsWithTips() {

    }

    private MatchdayPointsCalculator() {
        userPointsPersistenceService = LeaderboardPersistenceService.getInstance();
        userPersistenceService = UserPersistenceService.getInstance();
        matchTipPersistenceService = MatchTipPersistenceService.getInstance();
        leaguePersistenceService = LeaguePersistenceService.getInstance();
        MATCHDAY = getCurrentMatchday();
        userList = getAllUser();
    }

    private List<User> getAllUser () {
        return userPersistenceService.getAll();
    }

    private Matchday getCurrentMatchday() {
        return leaguePersistenceService.getCurrentLeagueByLeagueId("bl1").getCurrentMatchday();
    }

    private int getUserPointsFromMatchtips(final int userID) {
        List<MatchTip> userTips = matchTipPersistenceService.getByUserId(userID);
        int userPoints = 0;

        for(MatchTip tip : userTips) {
            userPoints += tip.getUserPoints();
        }
        return userPoints;
    }

    private void initializeUserPointsMatchday(User user) {
        UserPointsMatchday userPointsMatchday= new UserPointsMatchday();
        userPointsMatchday.setMatchday(MATCHDAY);
        userPointsMatchday.setPoints(getUserPointsFromMatchtips(user.getId()));
        userPointsMatchday.setuser(user);
        userPointsPersistenceService.saveOrUpdatePoints(userPointsMatchday);
    }
}
