package persistence;

import entities.MatchTip;
import entities.NewsfeedMessage;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class NewsfeedPersistenceService extends PersistenceService<NewsfeedMessage>{

	   private static NewsfeedPersistenceService instance;

	public static NewsfeedPersistenceService getInstance() {
		return instance = instance != null ? instance : new NewsfeedPersistenceService();
	}

	public List<NewsfeedMessage> getValidNewsfeedMessages() throws NoResultException {
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			Query query = entityManager.createQuery(
					"SELECT nm FROM NewsfeedMessage nm");
			query.setParameter("now1", new Date());
			query.setParameter("now2", new Date());
			List<NewsfeedMessage> newsfeedMessages = query.getResultList();
			if (newsfeedMessages.isEmpty()) {
				throw new NoResultException();
			}
			else {
				return newsfeedMessages;
			}
		});
	}
}
