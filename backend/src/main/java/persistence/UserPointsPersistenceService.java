package persistence;

import entities.UserPointsMatchday;

import javax.persistence.TypedQuery;

public class UserPointsPersistenceService extends PersistenceService<UserPointsMatchday> {
    private static UserPointsPersistenceService instance = new UserPointsPersistenceService();

    public static UserPointsPersistenceService getInstance() {
        return instance;
    }

    public void saveOrUpdatePoints(final UserPointsMatchday userPointsMatchday) {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<UserPointsMatchday> query = entityManager.createQuery("SELECT up FROM UserPointsMatchday up WHERE fk_user = :uID", UserPointsMatchday.class);
            query.setParameter("uID", userPointsMatchday.getuser().getId());

            if (query.getResultList().isEmpty()){
                this.save(userPointsMatchday);
            }
            else{
                UserPointsMatchday userPointsMatchdayNew = query.getSingleResult();
                userPointsMatchday.setPoints(userPointsMatchday.getPoints());
                this.update(userPointsMatchdayNew);
            }
        });
    }


}
