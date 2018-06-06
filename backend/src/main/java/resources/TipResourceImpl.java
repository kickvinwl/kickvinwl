package resources;

import com.sun.xml.internal.bind.v2.TODO;
import entities.MatchTip;
import entities.User;
import persistence.MatchTipPersistenceService;
import persistence.UserPersistenceService;
<<<<<<< HEAD
import resources.datamodel.Tip;
=======
import resources.datamodel.MatchTipTransform;
>>>>>>> 8ef4fbc496c241d6cfcf2609cc3f22e17ded390c

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
    public Response getTipByToken(String gameday, String token) {
        Response.ResponseBuilder response = Response.accepted();

        //Token -> User
        User user = UserPersistenceService.getInstance().getBySessionKey(token);

        //MatchTipTransform füllen
        MatchTipTransform matchTip = new MatchTipTransform();
        matchTip.setGameday(gameday);
        matchTip.setSeason("2017/18"); //TODO Season besorgen

        MatchTipTransform.MatchWithPoints matche;

        return response.entity(matchTip).build();
    }

    private MatchTipTransform.MatchWithPoints fillMatch()
    {
        MatchTipTransform.MatchWithPoints ret = new MatchTipTransform.MatchWithPoints();

        

        return ret;
    }
}
