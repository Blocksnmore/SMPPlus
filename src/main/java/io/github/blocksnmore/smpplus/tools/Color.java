package io.github.blocksnmore.smpplus.tools;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {

    private static final Pattern pattern = Pattern.compile("&(#[A-Fa-f0-9]{6})");

    public static String format(String message) {
        Matcher matcher = pattern.matcher(message); // Creates a matcher with the given pattern & message

        while (matcher.find()) { // Searches the message for something that matches the pattern
            String color = message.substring(matcher.start(), matcher.end()); // Extracts the color from the message
            message = message.replace(color, String.valueOf(ChatColor.of(color.substring(1)))); // Places the color in the message
        }

        return message; // Returns the message
    }

    public static String applyColor(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', format(message));
    }
}
