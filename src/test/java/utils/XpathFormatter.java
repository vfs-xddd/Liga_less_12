package utils;

public class XpathFormatter {

    public static String formatXpath(String rawPath, Object... args) {
        return String.format(rawPath, args);
    }

}
