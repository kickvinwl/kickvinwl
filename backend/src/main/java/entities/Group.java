package entities;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name ="squad")
public class Group extends EntityGeneratedKey {

	@Column(updatable = true, nullable = false)
	private String groupName;

	@Column(updatable = true, nullable = false)
	private String password;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="admin_ID")
	private User adminUser;

	@ManyToMany(mappedBy = "groups")
	private List<User> users = new LinkedList<>();

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

	public User isAdminUserId() {
		return adminUser;
	}
	public void setAdminUserId(User adminUser) {
		this.adminUser = adminUser;
	}

	public void addUserToGroup(User user) {
		users.add(user);
		//TODO: user-methode hinzufügen
		//user.addGroup(this);
	}



}
