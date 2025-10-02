package net.infumia.titleupdater;

import java.lang.reflect.Field;

final class NmsProvider {

    private static final int MAXIMUM_PATCH = 9;
    private static final String CLASS_PATTERN = "NmsV%s_%s%s";
    private static final String PACKAGE_NAME = "net.infumia.titleupdater.versions.";

    private static Nms nms;

    static Nms nms() {
        if (NmsProvider.nms == null) {
            NmsProvider.init();
        }
        return NmsProvider.nms;
    }

    private static void init() {
        final int major = Version.major();
        final int minor = Version.minor();
        final int patch = Version.patch();

        boolean next = false;
        for (int m = minor; m >= 0; m--) {
            for (int p = next ? MAXIMUM_PATCH : patch; p >= 0; p--) {
                final Nms found = NmsProvider.find(major, m, p);
                if (found != null) {
                    NmsProvider.nms = found;
                    return;
                }
            }
            next = true;
        }

        throw new IllegalStateException(
            "Unsupported server version: v" + major + "." + minor + "." + patch
        );
    }

    private static Nms find(final int major, final int minor, final int patch) {
        final String patchVersion = patch == 0 ? "" : "_" + patch;
        final String className = String.format(CLASS_PATTERN, major, minor, patchVersion);

        try {
            final Class<?> cls = Class.forName(PACKAGE_NAME + className);
            final Field field = cls.getDeclaredField("INSTANCE");
            return (Nms) field.get(null);
        } catch (final ClassNotFoundException ignored) {
            // ignored
        } catch (final ReflectiveOperationException exception) {
            throw new RuntimeException("Please contact us to report this error!", exception);
        }
        return null;
    }

    private NmsProvider() {
        throw new IllegalArgumentException("Utility class!");
    }
}
