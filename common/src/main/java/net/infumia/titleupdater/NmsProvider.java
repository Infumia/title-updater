package net.infumia.titleupdater;

import java.lang.reflect.Field;
import java.util.Objects;

final class NmsProvider {

    private static Nms nms;

    static void init() {
        final String classPattern = "NmsV%s_%s%s";
        final String packageName = "net.infumia.titleupdater.versions.";

        final int major = Version.major();
        final int minor = Version.minor();
        final int patch = Version.patch();

        Nms nms = null;
        for (int i = patch; i >= 0; --i) {
            final String patchVersion = i == 0 ? "" : "_" + i;
            final String className = String.format(classPattern, major, minor, patchVersion);

            try {
                final Class<?> cls = Class.forName(packageName + className);
                final Field field = cls.getDeclaredField("INSTANCE");
                nms = (Nms) field.get(null);
                break;
            } catch (final ClassNotFoundException ignored) {
                // ignored
            } catch (final ReflectiveOperationException exception) {
                throw new RuntimeException("Please contact us to report this error!", exception);
            }
        }

        NmsProvider.nms = Objects.requireNonNull(
            nms,
            "Unsupported server version: v" + major + "." + minor + "." + patch
        );
    }

    static Nms nms() {
        if (NmsProvider.nms == null) {
            NmsProvider.init();
        }
        return NmsProvider.nms;
    }

    private NmsProvider() {
        throw new IllegalArgumentException("Utility class!");
    }
}
