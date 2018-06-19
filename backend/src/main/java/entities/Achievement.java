package entities;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Achievement extends EntityGeneratedKey{

	@Column(updatable = true, nullable = true)
	private String achievementDescription;

	@Column(updatable = true, nullable = true)
	private String title;
/*
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_icon")
	private AchievementIcon icon;
*/
	@ManyToMany(mappedBy = "achievements")
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy = "displayedTitle")
	private List<User> presenter = new ArrayList<>();

	
	/*
	 * Needs to be a select that returns userIds
	 * all Ids get the achievement
	 */
	@Column(updatable = true, nullable = true)
	private String achievementQuerry;
	
	public String getAchievementQuerry() {
		return achievementQuerry;
	}
	public void setAchievementQuerry(String achievementQuerry) {
		this.achievementQuerry = achievementQuerry;
	}
	public String getAchievementDescription() {
		return achievementDescription;
	}
	public void setAchievementDescription(String achievementDescription) {
		this.achievementDescription = achievementDescription;
	}
	/*
	public AchievementIcon getIconId() {
		return icon;
	}
	public void setIconId(AchievementIcon icon) {
		this.icon = icon;
	}
	*/
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
