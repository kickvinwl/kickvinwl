package entities;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class NewsfeedMessage extends EntityGeneratedKey{
	//FK zu User
	@Column(updatable = false, nullable = false)
	private String userId;

	@Column(updatable = true, nullable = false)

	private String messageText;

	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)

	private Date startDate;
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isValid(){
		Date now = new Date();
		return getStartDate().getTime() < now.getTime() && getEndDate().getTime() > now.getTime();
	}
}
