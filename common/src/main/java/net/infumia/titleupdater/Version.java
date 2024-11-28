package net.infumia.titleupdater;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;

final class Version {

    private static final Pattern VERSION_PATTERN = Pattern.compile(
        "(?i)\\(MC: (\\d)\\.(\\d+)\\.?(\\d+?)?(?: (Pre-Release|Release Candidate) )?(\\d)?\\)"
    );

    private static int major = -1;
    private static int minor = -1;
    private static int patch = -1;
    private static Boolean paper = null;

    static void init() {
        final String version = Bukkit.getVersion();
        final Matcher matcher = Version.VERSION_PATTERN.matcher(version);
        int major = 1;
        int minor = 0;
        int patch = 0;
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }
        final MatchResult result = matcher.toMatchResult();
        try {
            major = Integer.parseInt(result.group(1), 10);
        } catch (final Exception ignored) {}
        try {
            minor = Integer.parseInt(result.group(2), 10);
        } catch (final Exception ignored) {}
        if (result.groupCount() >= 3) {
            try {
                patch = Integer.parseInt(result.group(3), 10);
            } catch (final Exception ignored) {}
        }
        Version.major = major;
        Version.minor = minor;
        Version.patch = patch;
        Version.paper = Reflection.hasClass("com.destroystokyo.paper.PaperConfig") ||
        Reflection.hasClass("io.papermc.paper.configuration.Configuration");
    }

    static int major() {
        Version.validate();
        return Version.major;
    }

    static int minor() {
        Version.validate();
        return Version.minor;
    }

    static int patch() {
        Version.validate();
        return Version.patch;
    }

    private static void validate() {
        if (
            Version.paper == null ||
            Version.major == -1 ||
            Version.minor == -1 ||
            Version.patch == -1
        ) {
            throw new IllegalArgumentException("Please parser the bukkit version first!");
        }
    }

    private Version() {
        throw new IllegalAccessError("Utility class!");
    }
}
