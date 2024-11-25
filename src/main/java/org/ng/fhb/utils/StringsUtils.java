package org.ng.fhb.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringsUtils {
    /**
     * Parses arguments from a command string, supporting quoted and unquoted multi-word arguments.
     * @param input The raw command input.
     * @return A list of parsed arguments.
     */
    public static List<String> parseArguments(String input) {
        List<String> args = new ArrayList<>();
        Matcher matcher = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                args.add(matcher.group(1)); // Quoted string
            } else {
                args.add(matcher.group(2)); // Unquoted string
            }
        }
        return args;
    }
}
