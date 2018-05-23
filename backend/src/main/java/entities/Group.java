package entities;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Group extends EntityGeneratedKey {
	@Column(updatable = true, nullable = false)
	private String groupName;
	@Column(updatable = true, nullable = false)
	private String password;
	//FK zu User
	@Column(updatable = true, nullable = false)
	private boolean adminUserId;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(boolean adminUserId) {
		this.adminUserId = adminUserId;
	}
	@OneToMany(mappedBy = "group")
	private Set<GroupUser> users = new HashSet<GroupUser>();
	
}
