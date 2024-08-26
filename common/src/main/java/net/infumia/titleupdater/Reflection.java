package net.infumia.titleupdater;

final class Reflection {

    static boolean hasClass(final String className) {
        try {
            Class.forName(className);
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    private Reflection() {
        throw new IllegalArgumentException("Utility class!");
    }
}
