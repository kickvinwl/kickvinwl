package de.mankianer.kickwinvl.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class Achievement{

	@Id
	@GeneratedValue
	private Long achievementID;

	private String achievementDescription;
	private String title;
	private String icon;
}
