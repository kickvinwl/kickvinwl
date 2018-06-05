package entities;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NewsfeedMessage extends EntityGeneratedKey{
	//FK zu User
	@Column(updatable = false, nullable = false)
	private String userId;
	
	@Column(updatable = true, nullable = false)
	private String messageText;
	@Column(updatable = true, nullable = false)
	private Date startDate;
	@Column(updatable = true, nullable = false)
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
	
}
