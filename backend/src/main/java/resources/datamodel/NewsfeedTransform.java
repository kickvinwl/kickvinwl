package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.NewsfeedMessage;

import java.util.ArrayList;
import java.util.List;

public class NewsfeedTransform {

    @JsonProperty
    private String messageTitle;

    @JsonProperty
    private String messageText;

    public static List<NewsfeedTransform> getTransform(List<NewsfeedMessage> news) {
        List<NewsfeedTransform> transformList = new ArrayList<>();
        for(NewsfeedMessage message : news ) {
            NewsfeedTransform messageTransform = new NewsfeedTransform();
            messageTransform.setMessageText(message.getMessageText());
            messageTransform.setMessageTitle(message.getMessageTitle());
            transformList.add(messageTransform);
        }
        return transformList;
    }


    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
