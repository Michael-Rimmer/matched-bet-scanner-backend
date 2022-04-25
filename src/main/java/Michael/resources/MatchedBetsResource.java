package Michael.resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import Michael.api.MatchedBet;
import Michael.core.Scraper;

@Path("/matched-bets")
@Produces(MediaType.APPLICATION_JSON)
public class MatchedBetsResource {

    public MatchedBetsResource() {
    }

//    @GET
//    @Timed
//    public Saying sayHello(@QueryParam("name") Optional<String> name) {
//        final String value = String.format(template, name.orElse(defaultName));
//        return new Saying(counter.incrementAndGet(), value);
//    }

    @GET
    @Timed
    public ArrayList<MatchedBet> getMatchedBets() {
        return Scraper.scrapeMatchedBets();
    }

}
