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

	public User getAdminUser() {
		return adminUser;
	}

	/**
	 *
	 * @param adminUser user-entity of the group creator. The mapping is bi-directional, so the admin-list in the user
	 *                  is also updated.
	 */
	public void setAdminUser(User adminUser) {
		this.adminUser = adminUser;
		//TODO: user-admin Liste hinzufügen
		//user.addAdminGroup(this);
	}

	/**
	 *
	 * @param user user to be added to the group. The mapping is bi-directional, so the group-list in the user is also
	 *             updated.
	 */
	public void addUserToGroup(User user) {
		users.add(user);
		//TODO: user-methode hinzufügen
		//user.addGroup(this);
	}

	/**
	 *
	 * @param user user to be removed from the group. If the user was an admin, the next user in the group list is set
	 *             to be the new admin
	 */
	public void removeUserFromGroup(User user) {
		users.remove(user);
		//TODO: wenn bei user implementiert einkommentieren
		// user.removeGroup(this)
		setNextAdmin();
	}


	private void setNextAdmin(){
		if (!users.isEmpty()) {
			setAdminUser(users.get(0));
		}
	}

	public boolean usersIsEmpty(){
		return users.isEmpty();
	}



}
