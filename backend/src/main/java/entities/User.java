package entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class User extends EntityGeneratedKey {
	@Column(updatable = false, nullable = false, unique = true)
	private String userName;
	@Column(updatable = true, nullable = false)
	private String userPicture;
	@Column(updatable = true, nullable = false)
	private boolean userIsAdmin;
	
	@JoinColumn(name = "id")
	@Column(updatable = true, nullable = false)
	private Achievement displayedTitle;
	
	@Column(updatable = true, nullable = false)
	private String sessionKey;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}

	public boolean isUserIsAdmin() {
		return userIsAdmin;
	}

	public void setUserIsAdmin(boolean userIsAdmin) {
		this.userIsAdmin = userIsAdmin;
	}

	public String getDisplayedTitle() {
		return displayedTitle.getTitle();
	}

	public void setDisplayedTitle(Achievement achievement) {
		this.displayedTitle = achievement;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	@OneToMany(mappedBy = "userG")
	private Set<GroupUser> groups = new HashSet<GroupUser>();
	@OneToMany(mappedBy = "userA")
	private Set<UserAchievement> achievements = new HashSet<UserAchievement>();
	
}
