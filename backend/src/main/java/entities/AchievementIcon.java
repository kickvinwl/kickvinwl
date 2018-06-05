package entities;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class AchievementIcon extends EntityGeneratedKey{

	@Column(updatable = true, nullable = false)
	private String iconUrl;

	/*
	TODO
	mapping between achievement icon and achievement does not work
	 */
/*
	@OneToMany(mappedBy = "icon")
	private List<Achievement> achievements = new ArrayList<Achievement>();
*/
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
		
}
