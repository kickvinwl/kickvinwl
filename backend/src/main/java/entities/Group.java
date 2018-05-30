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

	@ManyToMany(mappedBy = "groups")
	private List<User> users = new LinkedList<>();

	/* TODO: Mapping OneToMany
	//FK zu User
	@Column(updatable = true, nullable = false)
	private boolean adminUserId;
	*/

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
	/* TODO:siehe oben
	public boolean isAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(boolean adminUserId) {
		this.adminUserId = adminUserId;
	}
	*/



}
