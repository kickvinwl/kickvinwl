package manager;

import entities.NewsfeedMessage;
import entities.User;
import persistence.NewsfeedPersistenceService;
import resources.datamodel.NewsfeedTransform;
import util.DateTimeParser;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsfeedManager {

    /**
     * This method selects the valid news from the database and converts
     * it into a list of {@link resources.datamodel.NewsfeedTransform}.
     *
     * @return valid news as a list
     */
    public List<NewsfeedTransform> getValidNews() {
        List<NewsfeedTransform> newsTransform = new ArrayList<>();
        try {
            List<NewsfeedMessage> news = NewsfeedPersistenceService.getInstance().getValidNewsfeedMessages();
            newsTransform = NewsfeedTransform.getTransform(news);
        } catch (NoResultException e) {
        }
        return newsTransform;
    }

    /**
     * This method reads the input data and persists it as a
     * {@link entities.NewsfeedMessage}-entity.
     *
     * @param input input-data from the POST-request of the NewsfeedResource that
     *              has been passed through.
     * @param user  user, that is creating the news
     */
    public void postNewsMessage(Map<String, String> input, User user) throws ParseException {
        NewsfeedPersistenceService nps = NewsfeedPersistenceService.getInstance();
        String messageText = input.get("messageText");
        String messageTitle = input.get("messageTitle");
        String startDateString = input.get("startDate");
        String endDateString = input.get("endDate");
        NewsfeedMessage newMessage = new NewsfeedMessage();
        newMessage.setMessageTitle(messageTitle);
        newMessage.setMessageText(messageText);
        // no persistence if the format of the dates is not as defined
        newMessage.setStartDate(DateTimeParser.parseNewsfeedDate(startDateString));
        newMessage.setEndDate(DateTimeParser.parseNewsfeedDate(endDateString));
        newMessage.setUser(user);
        nps.save(newMessage);
    }
}
