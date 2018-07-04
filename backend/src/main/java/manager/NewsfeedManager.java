package manager;

import entities.NewsfeedMessage;
import entities.User;
import persistence.NewsfeedPersistenceService;
import resources.datamodel.NewsfeedTransform;
import util.DateTimeParser;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.NotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsfeedManager {

    /**
     * This method selects the valid news from the database and converts
     * it into a list of {@link resources.datamodel.NewsfeedTransform}.
     *
     * @return valid news as a list. if there are no news, the list is empty.
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
     * @param user  user that is creating the news
     */
    public void createNewsMessage(Map<String, String> input, User user) throws ParseException, SecurityException {
        if (!user.isUserIsAdmin()) {
            throw new SecurityException();
        }
        NewsfeedPersistenceService nps = NewsfeedPersistenceService.getInstance();
        String messageText = input.get("messageText");
        String messageTitle = input.get("messageTitle");
        String startDateString = input.get("startDate");
        String endDateString = input.get("endDate");
        NewsfeedMessage newMessage = new NewsfeedMessage();
        newMessage.setMessageTitle(messageTitle);
        newMessage.setMessageText(messageText);
        newMessage.setStartDate(DateTimeParser.parseNewsfeedDate(startDateString));
        newMessage.setEndDate(DateTimeParser.parseNewsfeedDate(endDateString));
        newMessage.setUser(user);
        try {
            nps.save(newMessage);
        } catch (PersistenceException e) {
            throw new ParseException("error in input data", 0);
        }
    }

    /**
     * This method deletes a NewsfeedMessage on the database.
     *
     * @param newsId id of the message that is to be deleted
     * @param user   user that sent the request
     * @throws NoResultException no message with passed id exists
     * @throws SecurityException user is not an admin and thus not authorized
     */
    public void deleteNewsMessage(final int newsId, final User user)
            throws NotFoundException, SecurityException {
        if (!user.isUserIsAdmin()) {
            throw new SecurityException();
        }
        NewsfeedPersistenceService nps = NewsfeedPersistenceService.getInstance();
        try {
            NewsfeedMessage message = nps.getNewsfeedMessageById(newsId);
            nps.delete(message);
        } catch (NoResultException e) {
            throw new NotFoundException();
        }

    }

}
