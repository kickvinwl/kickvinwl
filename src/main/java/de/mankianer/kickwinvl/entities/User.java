package de.mankianer.kickwinvl.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

  @Id
  @GeneratedValue
  private Long userID;

  private String userName;
  private String userPicture;
  private boolean userIsAdmin;
  @ManyToOne
  private Achievement displayedTitle;
  private String sessionKey;
  private LocalDate lastAction;


  @OneToMany(mappedBy = "owner")
  private List<MatchTip> tips = new ArrayList<>();

  @ManyToMany(mappedBy = "users")
  private List<Group> groups = new ArrayList<>();

  @ManyToMany
  private List<Achievement> achievements = new ArrayList<>();


  public User(String name) {
    this.userName = name;
    this.lastAction = LocalDate.now();//.plus(30, ChronoUnit.MINUTES);
//		this.setUserPicture("default");
  }

  public User() {
  }

  public void setDisplayedTitle(Achievement displayedTitle) {
    if (achievements.contains(displayedTitle)) {
      this.displayedTitle = displayedTitle;
    }

  }
  public void addTip(MatchTip tip) {
    tips.add(tip);
  }

  public void addAchievement(Achievement ach) {
    achievements.add(ach);
    if (getDisplayedTitle() == null) {
      setDisplayedTitle(ach);
    }
  }
}
