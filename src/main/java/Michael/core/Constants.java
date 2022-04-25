package Michael.core;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static enum BetMarket {
        WINNER_90_MINUTES,
    }

    public static enum BetOutcome {
        HOME, DRAW, AWAY,
    }

    public static enum Bookmaker {
        WILLIAMHILL, BETFAIR,
    }

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

//    DateTimePatternString="dd MMM yyyy HH:mm";

}
