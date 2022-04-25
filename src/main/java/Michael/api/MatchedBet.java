package Michael.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import Michael.core.Constants.BetMarket;
import Michael.core.Constants.BetOutcome;
import Michael.core.Constants.Bookmaker;
import Michael.core.FootballBet;

//junit tests
//float r = convertFractionalOddsToDecimal("8/15");
//System.out.println(r);
//r = convertFractionalOddsToDecimal("3/1");
//System.out.println(r);
//r = convertFractionalOddsToDecimal("24/5");
//System.out.println(r);
//r = convertFractionalOddsToDecimal("11/2");
//System.out.println(r);

public class MatchedBet {
    private final String details;
    private final LocalDateTime dateTime;
    private final BetMarket betMarket;
    private final BetOutcome betOutcome;
    private final Bookmaker bookmaker;
    private final Bookmaker exchange;
    private final float backOdds;
    private final float layOdds;
    private final int liquidity;
    private final String bookmakerWebLink;
    private final String exchangeWebLink;

    private float normalBetRating;
    private float snrBetRating;
    private float srBetRating;

    public MatchedBet(FootballBet backBet, FootballBet layBet) {
        this.details = backBet.getDetails();
        this.dateTime = backBet.getDateTime();
        this.betMarket = backBet.getBetMarket();
        this.betOutcome = backBet.getBetOutcome();
        this.bookmaker = backBet.getBookmaker();
        this.bookmakerWebLink = backBet.getWebLink();
        this.exchange = layBet.getBookmaker();
        this.exchangeWebLink = layBet.getWebLink();
        this.backOdds = backBet.getOdds();
        this.layOdds = layBet.getOdds();
        this.liquidity = layBet.getLiquidity();

        this.normalBetRating = calcNormalBetRating(backBet.getOdds(), layBet.getOdds());
        this.snrBetRating = calcSnrBetRating(backBet.getOdds(), layBet.getOdds());
        this.srBetRating = calcSrBetRating(backBet.getOdds(), layBet.getOdds());
    }

    public static float calcNormalBetRating(float backOdds, float layOdds) {
        return (backOdds / layOdds) * 100;
    }

    public static float calcSnrBetRating(float backOdds, float layOdds) {
        return ((backOdds - 1) / layOdds) * 100;
    }

    public static float calcSrBetRating(float backOdds, float layOdds) {
//        TODO this calculation may be incorrect
        return (1 + (backOdds / layOdds)) * 100;
    }

    public void setNormalBetRating(float normalBetRating) {
        this.normalBetRating = normalBetRating;
    }

    public void setSnrBetRating(float snrBetRating) {
        this.snrBetRating = snrBetRating;
    }

    public void setSrBetRating(float srBetRating) {
        this.srBetRating = srBetRating;
    }

    public static String generateKey(String... strings) {
        return String.join("", strings);
    }

    public String toString() {
        return String.format("%s @ %s\nBet Outcome: %s\nBack: %s @ %s\nLay: %s @ %s\nRating: %s", details, dateTime,
                betOutcome.name(), bookmaker.name(), backOdds, exchange.name(), layOdds, normalBetRating);
    }

    @JsonProperty
    public String getDetails() {
        return details;
    }

    @JsonProperty
    public String getDateTime() {
        return dateTime.toString();
    }

    @JsonProperty
    public BetMarket getBetMarket() {
        return betMarket;
    }

    @JsonProperty
    public BetOutcome getBetOutcome() {
        return betOutcome;
    }

    @JsonProperty
    public Bookmaker getBookmaker() {
        return bookmaker;
    }

    @JsonProperty
    public Bookmaker getExchange() {
        return exchange;
    }

    @JsonProperty
    public float getBackOdds() {
        return backOdds;
    }

    @JsonProperty
    public float getLayOdds() {
        return layOdds;
    }

    @JsonProperty
    public int getLiquidity() {
        return liquidity;
    }

    @JsonProperty
    public float getNormalBetRating() {
        return normalBetRating;
    }

    @JsonProperty
    public float getSnrBetRating() {
        return snrBetRating;
    }

    @JsonProperty
    public float getSrBetRating() {
        return srBetRating;
    }

    @JsonProperty
    public String getBookmakerWebLink() {
        return bookmakerWebLink;
    }

    @JsonProperty
    public String getExchangeWebLink() {
        return exchangeWebLink;
    }

}
