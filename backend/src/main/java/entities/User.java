package entities;

import java.util.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class User extends EntityGeneratedKey {

	@Column(updatable = false, nullable = false, unique = true)
	private String userName;
	@Column(updatable = true, nullable = false)
	private String userPicture;
	@Column(updatable = true, nullable = false)
	private boolean userIsAdmin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_displayedTitle")
	private Achievement displayedTitle;

	@Column(updatable = true, nullable = false)
	private String sessionKey;

	@Column(updatable = true, nullable = false)
	private Date lastAction;

	@Column(updatable = true, nullable = false)
	@OneToMany(mappedBy = "id")
	private List<Group> adminGroups = new ArrayList<>();

	@Column(updatable = true, nullable = true)
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<MatchTip> tips = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "user_squad",
			joinColumns = { @JoinColumn(name = "fk_user") },
			inverseJoinColumns = { @JoinColumn(name = "fk_squad")})
	private List<Group> groups = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "user_achievement",
			joinColumns = { @JoinColumn(name = "fk_user") },
			inverseJoinColumns = { @JoinColumn(name = "fk_achievement")})
	private List<Achievement> achievements = new ArrayList<>();

	public User(String name, String sessionKey)
	{
		this.userName = name;
		this.sessionKey = sessionKey;
		this.lastAction = new Date();
		this.setUserPicture("default");
		this.setUserIsAdmin(false);
	}

	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public Date getLastAction() {
		return lastAction;
	}

	public void setLastAction(Date lastAction) {
		this.lastAction = lastAction;
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

	//TODO: später einkommentieren
	//public String getDisplayedTitle() {
	//	return displayedTitle.getTitle();
	//}

	//public void setDisplayedTitle(Achievement achievement) {
	//	this.displayedTitle = achievement;
	//}

	public void addTip(MatchTip tip)
	{
		tips.add(tip);
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public List<MatchTip> getTips() {
		return tips;
	}
}
