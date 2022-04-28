package Michael.core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Michael.api.MatchedBet;
import Michael.core.Constants.BetMarket;
import Michael.core.Constants.BetOutcome;
import Michael.core.Constants.Bookmaker;

public class Scraper {

    private static HashMap<String, FootballBet> scrapeWilliamHill(WebDriver driver) {

        HashMap<String, FootballBet> bets = new HashMap<String, FootballBet>();

        driver.get(
                "https://sports.williamhill.com/betting/en-gb/football/competitions/OB_TY295/English-Premier-League/matches/OB_MGMB/Match-Betting");

        // find other competitions to scrape here -
        // https://sports.williamhill.com/betting/en-gb/football/competitions
        // driver.get("https://sports.williamhill.com/betting/en-gb/football/competitions/OB_TY338/Spanish-La-Liga-Primera/matches/OB_MGMB/Match-Betting");
        // TODO optimise timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        // TODO can I figure out the API call that the browser makes to get the data as
        // JSON?
        List<WebElement> eventElements = driver.findElements(By.cssSelector(".sp-o-market.sp-o-market--default"));

        String dateString = "";
        String timeString = "";

        for (WebElement e : eventElements) {

            String eventElementText = e.getText();
            String[] splitEventElementText = eventElementText.split("\\n");

            String homeTeam = "";
            String awayTeam = "";
            float backHomeOdds = -1;
            float backDrawOdds = -1;
            float backAwayOdds = -1;

            try {
                // Header element which contains date
                if (eventElementText.contains("90 Minutes")) {
                    dateString = splitEventElementText[0].split(", ")[1];
                }
                // Event element which contains time and H,D,A odds
                else if (eventElementText.contains(" v ")) {

                    // Format datetime
                    timeString = splitEventElementText[0];
                    LocalDateTime dateTime = LocalDateTime.parse(
                            String.format("%s %s %s", dateString, Year.now().getValue(), timeString),
                            Constants.dateTimeFormatter);

                    homeTeam = splitEventElementText[1].split(" v ")[0];
                    awayTeam = splitEventElementText[1].split(" v ")[1];

                    backHomeOdds = Helpers.convertFractionalOddsToDecimal(splitEventElementText[2]);
                    backDrawOdds = Helpers.convertFractionalOddsToDecimal(splitEventElementText[3]);
                    backAwayOdds = Helpers.convertFractionalOddsToDecimal(splitEventElementText[4]);

                    String webLink = e.findElement(By.tagName("a")).getAttribute("href");

                    // Create new FootballBet instances
                    FootballBet homeBet = new FootballBet(homeTeam, awayTeam, dateTime, BetMarket.WINNER_90_MINUTES,
                            BetOutcome.HOME, Bookmaker.WILLIAMHILL, backHomeOdds, webLink);

                    bets.put(homeBet.generateKey(), homeBet);

                    FootballBet drawBet = new FootballBet(homeTeam, awayTeam, dateTime, BetMarket.WINNER_90_MINUTES,
                            BetOutcome.DRAW, Bookmaker.WILLIAMHILL, backDrawOdds, webLink);

                    bets.put(drawBet.generateKey(), drawBet);

                    FootballBet awayBet = new FootballBet(homeTeam, awayTeam, dateTime, BetMarket.WINNER_90_MINUTES,
                            BetOutcome.AWAY, Bookmaker.WILLIAMHILL, backAwayOdds, webLink);

                    bets.put(awayBet.generateKey(), awayBet);
                }
            } catch (Exception ex) {
                System.out.println("Failed to parse, skipping");
                ex.printStackTrace();
            }
        }

        return bets;
    }

    private static HashMap<String, FootballBet> scrapeBetfair(WebDriver driver) {

        HashMap<String, FootballBet> bets = new HashMap<String, FootballBet>();

        driver.get("https://www.betfair.com/exchange/plus/en/football/english-premier-league-betting-10932509");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        // Dates are obtained from the banner above a collection of matches.
        // Therefore we use dateElementsIndex to correlate dates with matches.
        // This is done to make date formatting as simple as possible.
        List<WebElement> dateElements = driver.findElements(By.cssSelector(".card-header-title"));
        int dateElementsIndex = 0;

        List<WebElement> eventElements = driver.findElements(By.cssSelector(".card-content"));

        for (WebElement e : eventElements) {
            List<WebElement> eventListItems = e.findElements(By.tagName("tr"));
            for (WebElement event : eventListItems) {

                try {
                    String eventText = event.getText();

                    // Ignore header elements or elements with no text
                    if (event.getAttribute("class").contains("headers") || eventText.replaceAll("\\s+", "").isEmpty()) {
                        continue;
                    }

                    System.out.println("---");
                    System.out.println(event.getText());
                    System.out.println("---");

                    String[] eventElementText = eventText.split("\\n");

                    if (eventElementText[0].contains("'")) {
                        // Ignore matches that are in-play
                        continue;
                    }

                    // Format date time
                    String dateString = formatDateString(dateElements.get(dateElementsIndex).getText());

                    String timeString = "";
                    String[] timeStrings = eventElementText[0].split(" ");
                    timeString = timeStrings[timeStrings.length - 1];

                    LocalDateTime dateTime = LocalDateTime.parse(
                            String.format("%s %s %s", dateString, Year.now().getValue(), timeString),
                            Constants.dateTimeFormatter);

                    String homeTeam = eventElementText[1];
                    String awayTeam = eventElementText[2];

                    float layHomeOdds = Float.parseFloat(eventElementText[5]);
                    float layDrawOdds = Float.parseFloat(eventElementText[9]);
                    float layAwayOdds = Float.parseFloat(eventElementText[13]);

                    int layHomeLiquidity = Integer.parseInt(eventElementText[6].substring(1));
                    int layDrawLiquidity = Integer.parseInt(eventElementText[10].substring(1));
                    int layAwayLiquidity = Integer.parseInt(eventElementText[14].substring(1));

                    String webLink = event.findElement(By.tagName("a")).getAttribute("href");

                    // Add lay bets attributes to existing FootballMatch instances
                    // Create new FootballBet objects
                    FootballBet homeBet = new FootballBet(homeTeam, awayTeam, dateTime, BetMarket.WINNER_90_MINUTES,
                            BetOutcome.HOME, Bookmaker.BETFAIR, layHomeOdds, layHomeLiquidity, webLink);

                    bets.put(homeBet.generateKey(), homeBet);

                    FootballBet drawBet = new FootballBet(homeTeam, awayTeam, dateTime, BetMarket.WINNER_90_MINUTES,
                            BetOutcome.DRAW, Bookmaker.BETFAIR, layDrawOdds, layDrawLiquidity, webLink);

                    bets.put(drawBet.generateKey(), drawBet);

                    FootballBet awayBet = new FootballBet(homeTeam, awayTeam, dateTime, BetMarket.WINNER_90_MINUTES,
                            BetOutcome.AWAY, Bookmaker.BETFAIR, layAwayOdds, layAwayLiquidity, webLink);

                    bets.put(awayBet.generateKey(), awayBet);

                } catch (Exception ex) {
                    System.out.println("Failed to parse, skipping");
                    ex.printStackTrace();
                }

            }
            dateElementsIndex++;
        }

        return bets;
    }

    private static ArrayList<MatchedBet> generateMatchedBets(HashMap<String, FootballBet> backBets,
            HashMap<String, FootballBet> layBets) {

        ArrayList<MatchedBet> matchedBets = new ArrayList<>();

        for (String key : backBets.keySet()) {
            FootballBet layBet = layBets.getOrDefault(key, null);
            if (layBet != null) {
                matchedBets.add(new MatchedBet(backBets.get(key), layBet));
            }
        }

        return matchedBets;
    }

    public static ArrayList<MatchedBet> scrapeMatchedBets(String chromeDriverPath) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        HashMap<String, FootballBet> backBets = scrapeWilliamHill(driver);
        HashMap<String, FootballBet> layBets = scrapeBetfair(driver);

        System.out.println(String.format("\nFound %s back bets!", backBets.size()));
        System.out.println(String.format("\nFound %s lay bets!", layBets.size()));

        driver.quit();

        ArrayList<MatchedBet> matchedBets = generateMatchedBets(backBets, layBets);
        for (MatchedBet mb : matchedBets) {
            System.out.println("");
            System.out.println(mb);
        }

        System.out.println(String.format("\nFound %s matched bets!", matchedBets.size()));

        return matchedBets;
    }

    private static String formatDateString(String dateString) {

        String[] dateStringSplit = dateString.split(" ");
        Integer day = Integer.parseInt(dateStringSplit[1]);
        String month = dateStringSplit[2];

        System.out.println(dateStringSplit.toString());

        return String.format("%02d %s", day, month);
    }
}
