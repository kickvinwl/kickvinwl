package resources;

import com.sun.xml.internal.bind.v2.TODO;
import entities.MatchTip;
import entities.User;
import persistence.MatchTipPersistenceService;
import persistence.UserPersistenceService;
import resources.datamodel.Tip;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TipResourceImpl extends TipResource {

    @Override
    public Response setTip(String token, int gameday, String season, ArrayList<Tip> tipList) {
        response = Response.accepted().build();

        try {
            UserPersistenceService.getInstance().getBySessionKey(token);
            // TODO: Persistieren der Matches
            response = Response.accepted().build();
        }
        catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }

        return response;
    }



    @Override
    public Response getTipByToken(String token) {
        Response.ResponseBuilder response = Response.accepted();

        //Token -> User
        User user = UserPersistenceService.getInstance().getBySessionKey(token);

        //User -> MatchTip
        List<MatchTip> matchTips = MatchTipPersistenceService.getInstance().getByUserId(user.getId());


        return response.entity(matchTips.toArray()).build();
    }
}
