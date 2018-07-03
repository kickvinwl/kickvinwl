package entities;

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


    //TODO: Das kann weg?
	//@OneToMany(mappedBy = "displayedTitle", fetch = FetchType.LAZY)
	//private List<User> presenter = new ArrayList<>();
	
	/*
	 * Needs to be a select that returns user
	 * all users get the achievement
	 */
	@Column(updatable = true, nullable = true, length = 700)
	private String achievementQuery;
	
	public String getAchievementQuery() {
		return achievementQuery;
	}
	public void setAchievementQuery(String achievementQuery) {
		this.achievementQuery = achievementQuery;
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

	//TODO: unnötig
	@Override
	public String toString() {
		return "Achievement [achievementDescription=" + achievementDescription + ", title=" + title + ", achievementQuery=" + achievementQuery + "]";
	}	
}
