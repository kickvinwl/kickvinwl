package entities;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Achievement extends EntityGeneratedKey{
	
		
	@Column(updatable = true, nullable = false)
	private String achievementName;
	@Column(updatable = true, nullable = false)
	private String achievementDescription;
	@Column(updatable = true, nullable = false)
	private int iconId;
	@Column(updatable = true, nullable = false)
	private String Title;
	
	public String getAchievementName() {
		return achievementName;
	}
	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}
	public String getAchievementDescription() {
		return achievementDescription;
	}
	public void setAchievementDescription(String achievementDescription) {
		this.achievementDescription = achievementDescription;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
}
