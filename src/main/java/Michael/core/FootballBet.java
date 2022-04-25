package Michael.core;

import java.time.LocalDateTime;

import Michael.core.Constants.BetMarket;
import Michael.core.Constants.BetOutcome;
import Michael.core.Constants.Bookmaker;

public class FootballBet {

    private final String homeTeam;
    private final String awayTeam;
    private final LocalDateTime dateTime;
    private final BetMarket betMarket;
    private final BetOutcome betOutcome;
    private final Bookmaker bookmaker;
    private final float odds;
    private final int liquidity;
    private final String webLink;

    public FootballBet(String homeTeam, String awayTeam, LocalDateTime dateTime, BetMarket betMarket,
            BetOutcome betOutcome, Bookmaker bookmaker, float odds, String webLink) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.dateTime = dateTime;
        this.betMarket = betMarket;
        this.betOutcome = betOutcome;
        this.bookmaker = bookmaker;
        this.odds = odds;
        this.liquidity = -1;
        this.webLink = webLink;
    }

    public FootballBet(String homeTeam, String awayTeam, LocalDateTime dateTime, BetMarket betMarket,
            BetOutcome betOutcome, Bookmaker bookmaker, float odds, int liquidity, String webLink) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.dateTime = dateTime;
        this.betMarket = betMarket;
        this.betOutcome = betOutcome;
        this.bookmaker = bookmaker;
        this.odds = odds;
        this.liquidity = liquidity;
        this.webLink = webLink;
    }

    public String generateKey() {
        return String.format("%s%s%s%s%s", homeTeam, awayTeam, dateTime.toString(), betMarket.toString(),
                getBetOutcome().toString());
    }

    public String eventToString() {
        return String.format("%s vs %s @ %s", homeTeam, awayTeam, dateTime.toString());
    }

    public String getDetails() {
        return String.format("%s vs %s", homeTeam, awayTeam);
    }

    public float getOdds() {
        return odds;
    }

    public BetOutcome getBetOutcome() {
        return betOutcome;
    }

    public Bookmaker getBookmaker() {
        return bookmaker;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BetMarket getBetMarket() {
        return betMarket;
    }

    public int getLiquidity() {
        return liquidity;
    }

    public String getWebLink() {
        return webLink;
    }

}
