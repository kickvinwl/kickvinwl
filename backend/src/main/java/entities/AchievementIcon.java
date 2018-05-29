package entities;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class AchievementIcon extends EntityGeneratedKey{

	
	@Column(updatable = true, nullable = false)
	private String iconUrl;

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	@OneToMany(mappedBy = "icon")
	private Set<Achievement> achievements = new HashSet<Achievement>();
		
}
