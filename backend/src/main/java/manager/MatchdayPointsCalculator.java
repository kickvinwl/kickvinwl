package manager;

import entities.*;
import persistence.*;

import java.util.List;

public class MatchdayPointsCalculator {
    private LeaderboardPersistenceService userPointsPersistenceService;
    private UserPersistenceService userPersistenceService;
    private MatchTipPersistenceService matchTipPersistenceService;
    private MatchPersistenceService matchPersistenceService;
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
         userList.forEach(this::calculateUserPointsAndWriteInMatchTipPoints);
    }

    private Match getActualMatchResult(final int matchID) {
        return matchPersistenceService.getMatchById(matchID);
    }

    private MatchdayPointsCalculator() {
        matchPersistenceService = MatchPersistenceService.getInstance();
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


    private void calculateUserPointsAndWriteInMatchTipPoints(final User user) {
        List<MatchTip> userTips = matchTipPersistenceService.getByUserId(user.getId());
        Match actualMatchResult;
        for(MatchTip tip : userTips) {
            actualMatchResult = getActualMatchResult(tip.getTippedMatch().getMatchID());
            tip.setUserPoints(calculatePointsFromTippedMatch(actualMatchResult, tip));
        }
    }

    private int calculatePointsFromTippedMatch(Match actualMatchResult, MatchTip tips ) {
        if(actualMatchResult.getMatchID() == tips.getTippedMatch().getMatchID()) {
            if(actualMatchResult.getGoalsTeam1() == tips.getGoalsTeam1() && actualMatchResult.getGoalsTeam2() == tips.getGoalsTeam2()) {
                return 4; //genau richtig getippt
            }

            if(actualMatchResult.getGoalsTeam1() > actualMatchResult.getGoalsTeam2() && tips.getGoalsTeam1() > tips.getGoalsTeam2()) {
                return 2; //Team 1 hat gewonnen
            }

            if(actualMatchResult.getGoalsTeam2() > actualMatchResult.getGoalsTeam1() && tips.getGoalsTeam2() > tips.getGoalsTeam1()) {
                return 2; //Team 2 hat gewonnen
            }
            return -1;
        }
        else
        {
            return -1;
        }
    }

    private void initializeUserPointsMatchday(User user) {
        UserPointsMatchday userPointsMatchday= new UserPointsMatchday();
        userPointsMatchday.setMatchday(MATCHDAY);
        userPointsMatchday.setPoints(getUserPointsFromMatchtips(user.getId()));
        userPointsMatchday.setuser(user);
        userPointsPersistenceService.saveOrUpdatePoints(userPointsMatchday);
    }
}
