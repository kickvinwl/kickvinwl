package de.mankianer.kickwinvl.entities;

import java.time.LocalDate;
import lombok.Data;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Entity
public class NewsfeedMessage {

  @Id
  @GeneratedValue
  private Long newsFeedID;

  @ManyToOne
  private User author;

  private String messageTitle;
  private String messageText;
  private LocalDate startDate;
  private LocalDate endDate;
}
