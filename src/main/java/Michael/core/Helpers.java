package Michael.core;

public class Helpers {

    public static float convertFractionalOddsToDecimal(String fractionOdds) {

        if (fractionOdds.toUpperCase().contains("EVS")) {
            return 2;
        }

        try {
            int numerator = Integer.parseInt(fractionOdds.split("/")[0]);
            int denominator = Integer.parseInt(fractionOdds.split("/")[1]);
            float decimalOdds = (float) numerator / denominator + 1;
            return (float) (Math.round(decimalOdds * 100.0) / 100.0);
        } catch (Exception NumberFormatException) {
            // default value when parsing odds fails
            System.out.println(String.format("Failed to convert odds for string: %s", fractionOdds));
            return -1;
        }
    }
}
