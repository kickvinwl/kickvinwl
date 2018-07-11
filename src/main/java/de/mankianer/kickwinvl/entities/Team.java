package de.mankianer.kickwinvl.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Team {

  @Id
  @GeneratedValue
  private Long teamID;

  private int ext_teamId;

  private String teamName;
  private String teamIconURL;

  public Team() {
    teamIconURL = "default";
  }
}
