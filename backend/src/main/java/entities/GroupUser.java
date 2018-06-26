package entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
public class GroupUser {
	@Embeddable
	public static class GroupUserId implements Serializable {

		@Column(name = "fk_user")
		protected int userId;

		@Column(name = "fk_group")
		protected int groupId;

		public GroupUserId() {			
		}
		
		public GroupUserId(int userId, int groupId) {
			this.userId = userId;
			this.groupId = groupId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}				
		
	}
}
