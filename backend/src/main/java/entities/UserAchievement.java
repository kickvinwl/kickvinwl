package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

public class UserAchievement {
	@Embeddable
	public static class UserAchievementId implements Serializable {

		@Column(name = "fk_user")
		protected User userId;

		@Column(name = "fk_achievement")
		protected Achievement achievementId;

		public UserAchievementId() {
			
		}
		
		public UserAchievementId(User userId, Achievement achievementId) {
			this.userId = userId;
			this.achievementId = achievementId;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			
			UserAchievementId other = (UserAchievementId) obj;
			
			if (userId == null) {
				if (other.userId != null)
					return false;
			} else if (!userId.equals(other.userId))
				return false;
			
			if (achievementId == null) {
				if (other.achievementId != null)
					return false;
			} else if (!achievementId.equals(other.achievementId))
				return false;
			
			return true;
		}
	}

}
