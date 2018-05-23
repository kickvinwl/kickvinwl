package entities;

import javax.persistence.Column;

public class User extends EntityGeneratedKey {
	@Column(updatable = true, nullable = false)
	private String userName;
	@Column(updatable = true, nullable = false)
	private String userPicture;
	@Column(updatable = true, nullable = false)
	private boolean userIsAdmin;
	
	//FK zu Achievement
	@Column(updatable = true, nullable = false)
	private String displayedTitle;
	
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
		return displayedTitle;
	}

	public void setDisplayedTitle(String displayedTitle) {
		this.displayedTitle = displayedTitle;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	
}
