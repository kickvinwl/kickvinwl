package persistence;

import entities.Achievement;
import entities.Group;
import entities.MatchTip;
import entities.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.Date;
import java.util.List;

public class UserPersistenceService extends PersistenceService<User> {

    private static UserPersistenceService instance;

    private static long SESSION_LENGTH = 30 * 60 * 1000;

    public static UserPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new UserPersistenceService();
    }
    private UserPersistenceService() {};

    public User getByName(final String userName) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT us FROM User us WHERE userName = :userName");
            query.setParameter("userName", userName);
            List<User> user = query.getResultList();
            if(user.isEmpty())
                throw new NoResultException();
            else
                return user.get(0);
        });
    }
    public User getById(final int id) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT us FROM User us WHERE id = :id");
            query.setParameter("id", id);
            List<User> user = query.getResultList();
            if(user.isEmpty())
                throw new NoResultException();
            else
                return user.get(0);
        });
    }

    public void updateLastAction(final User user) throws EntityNotFoundException {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            User usertmp = user;
            usertmp.setLastAction(new Date());
            this.update(usertmp);
        });
    }


    public boolean exists(final String userName){
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT us FROM User us WHERE userName = :name");
            query.setParameter("name", userName);
            List<User> user = query.getResultList();
            return !user.isEmpty();
        });
    }

    public User getBySessionKey(final String sessionKey) throws NoResultException, SecurityException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT us FROM User us WHERE us.sessionKey = :sKey");
            query.setParameter("sKey", sessionKey);
            List<User> user = query.getResultList();
            if(user.isEmpty())
                throw new NoResultException();
            else
                if(user.get(0).getLastAction().getTime() <= System.currentTimeMillis() - SESSION_LENGTH) throw new SecurityException("Session ausgelaufen!");
                User u = user.get(0);
                u.setTips(loadMatchTips(u.getId()));
                return user.get(0);
        });
    }

    public void deleteByUserName(final String userName) throws EntityNotFoundException{
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("DELETE FROM User WHERE userName = :userName");
            query.setParameter("userName", userName);
            int deleted = query.executeUpdate();
            if (deleted == 0)
                throw new EntityNotFoundException();
        });
    }

    @SuppressWarnings("unchecked")
    public List<Group> getGroups(final User user) throws EntityNotFoundException{
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT g FROM Group g INNER JOIN GroupUser gu ON g.id=gu.fk_group WHERE gu.fk_user = :userId");
            query.setParameter("userId", user.getId());
            return query.getResultList();
        });
    }

    public int getCurrentPoints(final String userName) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            //TODO
            return 42;
        });
    }

    public int getCurrentRank(final String userName) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            //TODO
            return 1;
        });
    }

    public int getPointsBestMatchday(final String userName) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            //TODO
            return 12;
        });
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT * FROM User");
            return query.getResultList();
        });
    }
    @SuppressWarnings("unchecked")
    public List<User> getSearch(String search) {
    	return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
    		Query query = entityManager.createQuery("SELECT u FROM User u WHERE userName LIKE '%" + search + "%'");
    		return query.getResultList();
    	});
    }

    public boolean hasEntries(){
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT a FROM User a");
            List<Achievement> ach = query.getResultList();
            return !ach.isEmpty();
        });
    }

    private List<MatchTip> loadMatchTips(final int userID) {
        MatchTipPersistenceService mtps = MatchTipPersistenceService.getInstance();
        return mtps.getByUserId(userID);
    }

}