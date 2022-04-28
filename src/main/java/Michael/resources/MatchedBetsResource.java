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

    private final String chromeDriverPath;

    public MatchedBetsResource(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;
    }

    @GET
    @Timed
    public ArrayList<MatchedBet> getMatchedBets() {
        return Scraper.scrapeMatchedBets(chromeDriverPath);
    }

}
