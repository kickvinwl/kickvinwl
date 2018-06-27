package persistence;

import entities.NewsfeedMessage;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class NewsfeedPersistenceService extends PersistenceService<NewsfeedMessage> {

    private static NewsfeedPersistenceService instance;

    public static NewsfeedPersistenceService getInstance() {
        return instance = instance != null ? instance : new NewsfeedPersistenceService();
    }

    /**
     *
     * @return list of messages, that are still valid
     * @throws NoResultException no valid messages have been foud
     */
    public List<NewsfeedMessage> getValidNewsfeedMessages() throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery(
                    "SELECT nm FROM NewsfeedMessage nm where nm.startDate < :now and nm.endDate > :now " +
                            " ORDER BY nm.startDate DESC");
            Date now = new Date();
            query.setParameter("now", now);
            List<NewsfeedMessage> newsfeedMessages = query.getResultList();
            if (newsfeedMessages.isEmpty()) {
                throw new NoResultException();
            } else {
                return newsfeedMessages;
            }
        });
    }

    /**
     *
     * @param newsId id of the message
     * @return NewsfeedMessage object
     * @throws NoResultException no message with parameter id
     */
    public NewsfeedMessage getNewsfeedMessageById(final int newsId) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery(
                    "SELECT nm FROM NewsfeedMessage nm where nm.id = :newsId ");
            query.setParameter("newsId", newsId);
            List<NewsfeedMessage> newsfeedMessages = query.getResultList();
            if (newsfeedMessages.isEmpty()) {
                throw new NoResultException();
            } else {
                return newsfeedMessages.get(0);
            }
        });
    }
}