package de.mankianer.kickwinvl.entities;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name ="squad")
public class Group {

	@Id
	@GeneratedValue
	private Long groupeID;

	private String groupName;
	private String password;

	@ManyToOne
	private User adminUser;

	@ManyToMany
	private List<User> users = new ArrayList<>();


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
