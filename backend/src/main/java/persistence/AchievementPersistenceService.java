package persistence;

import entities.Achievement;
import entities.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
public class AchievementPersistenceService extends PersistenceService<Achievement>{

	private static AchievementPersistenceService instance;

	public static AchievementPersistenceService getInstance() {
		return instance = instance != null ? instance : new AchievementPersistenceService();
	}
	private AchievementPersistenceService() {};

	/**
	 * @param achievementName
	 * @return
	 * @throws NoResultException
	 */
	public Achievement getByAchievementName(final String achievementName)throws NoResultException{
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			String qlString = "SELECT a FROM achievement a where achievementName = :aName";
			Query query = entityManager.createQuery(qlString);
			query.setParameter("aName", achievementName);
			List<Achievement> achievement = query.getResultList();
			if (achievement.isEmpty())
				throw new NoResultException();
			return achievement.get(0);
		});
	}

	/**
	 * @param userName
	 * @return
	 * @throws NoResultException
	 */
	public List<Achievement> getAllByUserName(final String userName)throws NoResultException{
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			String qlString = "SELECT achievement FROM Achievement as a " +
					"INNER JOIN a.user as u " +
					"WHERE u.userName = :uName";
			Query query = entityManager.createQuery(qlString);
			query.setParameter("uName", userName);
			List<Achievement> achievement = query.getResultList();
			if (achievement.isEmpty())
				throw new NoResultException();
			return achievement;
		});
	}
	public List<Achievement> getAll() throws NoResultException{
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			String qlString = "SELECT a FROM Achievement a";
			Query query = entityManager.createQuery(qlString);
			List<Achievement> achievements = query.getResultList();
			if (achievements.isEmpty())
				throw new NoResultException();
			return achievements;
		});
	}


	public boolean hasEntries(){
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			Query query = entityManager.createQuery("SELECT a FROM Achievement a");			
			List<Achievement> ach = query.getResultList();
			return !ach.isEmpty();
		});
	}


}