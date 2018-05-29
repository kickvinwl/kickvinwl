package persistence;

import entities.Group;
import entities.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UserPersistenceService extends PersistenceService<User> {

	private static UserPersistenceService instance;

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

	public void delete(final User user) throws EntityNotFoundException{
		JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			Query query = entityManager.createQuery("DELETE FROM User WHERE userName = :userName");
			query.setParameter("userName", user.getUserName());
			int deleted = query.executeUpdate();
			if (deleted == 0)
				throw new EntityNotFoundException();			
		});
	}
	
	public void update(final User user) throws EntityNotFoundException{
		JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			Query query = entityManager.createQuery("UPDATE User SET (userPicture, isUserAdmin, displayedTitle, sessionKey) VALUES (:pic, :admin, :title, :session) WHERE userName= :userName");
			query.setParameter("pic", user.getDisplayedTitle());
			query.setParameter("admin", user.isUserIsAdmin());
			query.setParameter("title", user.getDisplayedTitle());
			query.setParameter("session", user.getSessionKey());
			query.setParameter("userName", user.getUserName());
			int updated = query.executeUpdate();
			if (updated == 0) 
				throw new EntityNotFoundException();		
		});
	}
	@SuppressWarnings("unchecked")
	public List<Group> getGroupsNames(final User user) throws EntityNotFoundException{
		return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			Query query = entityManager.createQuery("SELECT g FROM Group g INNER JOIN GroupUser gu ON g.id=gu.fk_group WHERE gu.fk_user = :userId");
			query.setParameter("userId", user.getId());
			return query.getResultList();
		});
	}
	
	public void save(final User user) throws EntityExistsException {
		JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
			try {
				this.getByName(user.getUserName());
				throw new EntityExistsException();
			}catch(NoResultException e) {
				//User nicht gefunden, also darf er erstellt werden
				Query query = entityManager.createQuery("INSERT INTO User (userPicture, isUserAdmin, displayedTitle, sessionKey) VALUES (:pic, :admin, :title, :session)");
				query.setParameter("pic", user.getDisplayedTitle());
				query.setParameter("admin", user.isUserIsAdmin());
				query.setParameter("title", user.getDisplayedTitle());
				query.setParameter("session", user.getSessionKey());
				query.setParameter("userName", user.getUserName());
				return query.executeUpdate();
			}
			
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


	public static UserPersistenceService getInstance()
	{
		return instance = instance != null ? instance : new UserPersistenceService();
	}
}
