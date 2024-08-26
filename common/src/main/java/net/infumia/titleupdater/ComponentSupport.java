package net.infumia.titleupdater;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Utility class for handling serialization and deserialization of {@link Component}s.
 * <p>
 * By default, the deserializer is {@link LegacyComponentSerializer} which uses ampersand to deserialize the given title.
 */
public final class ComponentSupport {

    private static ComponentSerializer<Component, ?, String> deserializer =
        LegacyComponentSerializer.legacyAmpersand();

    /**
     * Sets the deserializer to be used for converting strings into {@link Component}s.
     * <p>
     * You can set it to {@code null} to disable component support, but you cannot give {@link Component} object as the title.
     *
     * @param deserializer the component serializer to set.
     */
    public static void deserializer(final ComponentSerializer<Component, ?, String> deserializer) {
        ComponentSupport.deserializer = deserializer;
    }

    private static final GsonComponentSerializer serializer = GsonComponentSerializer.gson();

    static Object serialize(final Object title) {
        if (!(title instanceof String) && !(title instanceof Component)) {
            throw new IllegalStateException("Object must be either String or Component!");
        }
        if (ComponentSupport.deserializer == null) {
            if (title instanceof Component) {
                throw new IllegalArgumentException(
                    "Deserializer set null but title provided as Component!"
                );
            }
            return title;
        }
        final Component component;
        if (title instanceof String) {
            component = ComponentSupport.deserializer.deserialize((String) title);
        } else {
            component = (Component) title;
        }
        return ComponentSupport.serializer.serializeToTree(component);
    }

    private ComponentSupport() {
        throw new IllegalArgumentException("Utility class!");
    }
}
