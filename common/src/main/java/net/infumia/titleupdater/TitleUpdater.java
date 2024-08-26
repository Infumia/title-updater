package net.infumia.titleupdater;

import com.google.gson.JsonElement;
import java.util.Objects;
import org.bukkit.entity.Player;

/**
 * The class provides functionality to update the title of a player.
 */
public final class TitleUpdater {

    /**
     * Updates the title of the specified player with the given title object.
     * <p>
     * {@code title} must be either {@link String}, kyori's Component or {@link JsonElement}.
     * <p>
     * Checks if the server is a Paper spigot, above 1.16+ and has kyori's Component class in the classpath.
     *
     * @param player the player whose title will be updated. Cannot be null.
     * @param title  the new title to set for the player. Cannot be null.
     */
    public static void update(final Player player, final Object title) {
        Objects.requireNonNull(player, "Player cannot be null!");
        Objects.requireNonNull(title, "Title cannot be null!");
        Object newTitle = title;
        if (Internal.supportsComponents()) {
            newTitle = ComponentSupport.serialize(newTitle);
        }
        NmsProvider.nms().updateTitle(player, newTitle);
    }

    static {
        Version.init();
    }

    private TitleUpdater() {
        throw new IllegalAccessError("Utility class!");
    }
}
