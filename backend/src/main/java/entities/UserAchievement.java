package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

public class UserAchievement {
	@Embeddable
	public static class UserAchievementId implements Serializable {

		@Column(name = "fk_user")
		protected int userId;

		@Column(name = "fk_achievement")
		protected int achievementId;

		public UserAchievementId() {
			
		}
		
		public UserAchievementId(int userId, int achievementId) {
			this.userId = userId;
			this.achievementId = achievementId;
		}
	}

}
