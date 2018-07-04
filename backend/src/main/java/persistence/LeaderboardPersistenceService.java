package persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import entities.Group;
import entities.UserPointsMatchday;
import resources.datamodel.UserPoints;

import entities.Group;
import entities.UserPointsMatchday;
import resources.datamodel.UserPoints;

/**
 * Alle Datenbank-Funktionen für die zugehörige Datenklasse
 */
public class LeaderboardPersistenceService extends PersistenceService<UserPointsMatchday> {

	private static LeaderboardPersistenceService instance;

	public static LeaderboardPersistenceService getInstance() {
		return instance = instance != null ? instance : new LeaderboardPersistenceService();
	}

	private LeaderboardPersistenceService() {
	}

	public List<UserPoints> getAlltimeLeaderboard() throws NoResultException {
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			String queryString = "SELECT new resources.datamodel.UserPoints(-1, leaderboard.user.userName, SUM(points)) FROM UserPointsMatchday leaderboard group by leaderboard.user order by points desc";
			TypedQuery<UserPoints> query = entityManager.createQuery(queryString, UserPoints.class);
			List<UserPoints> resultList = query.getResultList();
			if (resultList.isEmpty())
				throw new NoResultException();
			else {
				for (int i = 0; i < resultList.size(); i++) {
					resultList.get(i).setPlatzierung(i+1);
				}
				return resultList;
			}
		});
	}

	public List<UserPoints> getSeasonLeaderboard(final int leagueID) throws NoResultException {
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			TypedQuery<UserPoints> query = entityManager.createQuery(
					"SELECT new resources.datamodel.UserPoints(-1, leaderboard.user.userName, SUM(leaderboard.points)) FROM UserPointsMatchday leaderboard WHERE leaderboard.matchday.league.id = :leagueID group by leaderboard.user order by points desc",
					UserPoints.class);
			query.setParameter("leagueID", leagueID);
			List<UserPoints> resultList = query.getResultList();
			if (resultList.isEmpty())
				throw new NoResultException();
			else {
				for (int i = 0; i < resultList.size(); i++) {
					resultList.get(i).setPlatzierung(i+1);
				}
				return resultList;
			}
		});
	}

	public List<UserPoints> getGamedayLeaderboard(final int gamedayID) throws NoResultException {
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			TypedQuery<UserPointsMatchday> query = entityManager.createQuery(
					"SELECT leaderboard FROM UserPointsMatchday leaderboard WHERE leaderboard.matchday.id = :gamedayID order by points desc",
					UserPointsMatchday.class);
			query.setParameter("gamedayID", gamedayID);
			List<UserPointsMatchday> resultList = query.getResultList();
			if (resultList.isEmpty())
				throw new NoResultException();
			else {
				List<UserPoints> leaderboard = new ArrayList<>();
				for (int i = 0; i < resultList.size(); i++) {
					UserPointsMatchday result = resultList.get(i);
					leaderboard.add(new UserPoints(i + 1, result.getuser().getUserName(), result.getPoints()));
				}
				return leaderboard;
			}
		});
	}

	public void saveOrUpdatePoints(final UserPointsMatchday userPointsMatchday) {
		JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			TypedQuery<UserPointsMatchday> query = entityManager
					.createQuery("SELECT up FROM UserPointsMatchday up WHERE fk_user = :uID", UserPointsMatchday.class);
			query.setParameter("uID", userPointsMatchday.getuser().getId());

			if (query.getResultList().isEmpty()) {
				this.save(userPointsMatchday);
			} else {
				UserPointsMatchday userPointsMatchdayNew = query.getSingleResult();
				userPointsMatchday.setPoints(userPointsMatchday.getPoints());
				this.update(userPointsMatchdayNew);
			}
		});
	}

	public List<UserPoints> getAlltimeLeaderboard(Group group) throws NoResultException {
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			String queryString = "SELECT new resources.datamodel.UserPoints(-1, leaderboard.user.userName, SUM(points)) FROM UserPointsMatchday leaderboard WHERE leaderboard.user.id IN(Select g.userId FROM Group g WHERE g.groupId = :groupId) group by leaderboard.user order by points desc";
			TypedQuery<UserPoints> query = entityManager.createQuery(queryString, UserPoints.class);
			query.setParameter("groupId", group.getId());
			List<UserPoints> resultList = query.getResultList();
			if (resultList.isEmpty())
				throw new NoResultException();
			else {
				for (int i = 0; i < resultList.size(); i++) {
					resultList.get(i).setPlatzierung(i+1);
				}
				return resultList;
			}
		});
	}

	public List<UserPoints> getSeasonLeaderboard(int leagueID, Group group) {
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			TypedQuery<UserPoints> query = entityManager.createQuery(
					"SELECT new resources.datamodel.UserPoints(-1, leaderboard.user.userName, SUM(leaderboard.points)) FROM UserPointsMatchday leaderboard WHERE leaderboard.matchday.league.id = :leagueID AND leaderboard.user.id IN(Select g.userId FROM Group g WHERE g.groupId = :groupId) group by leaderboard.user order by points desc",
					UserPoints.class);
			query.setParameter("leagueID", leagueID);
			query.setParameter("groupId", group.getId());
			List<UserPoints> resultList = query.getResultList();
			if (resultList.isEmpty())
				throw new NoResultException();
			else {
				for (int i = 0; i < resultList.size(); i++) {
					resultList.get(i).setPlatzierung(i+1);
				}
				return resultList;
			}
		});
	}

	public List<UserPoints> getGamedayLeaderboard(int gamedayID, Group group) {
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			TypedQuery<UserPointsMatchday> query = entityManager.createQuery(
					"SELECT leaderboard FROM UserPointsMatchday leaderboard WHERE leaderboard.matchday.id = :gamedayID AND leaderboard.user.id IN(Select g.userId FROM Group g WHERE g.groupId = :groupId) order by points desc",
					UserPointsMatchday.class);
			query.setParameter("gamedayID", gamedayID);
			query.setParameter("groupId", group.getId());
			List<UserPointsMatchday> resultList = query.getResultList();
			if (resultList.isEmpty())
				throw new NoResultException();
			else {
				List<UserPoints> leaderboard = new ArrayList<>();
				for (int i = 0; i < resultList.size(); i++) {
					UserPointsMatchday result = resultList.get(i);
					leaderboard.add(new UserPoints(i + 1, result.getuser().getUserName(), result.getPoints()));
				}
				return leaderboard;
			}
		});
	}
}
