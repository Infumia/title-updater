package net.infumia.titleupdater;

final class Internal {

    private static Boolean supportsComponent = null;

    static boolean supportsComponents() {
        if (Internal.supportsComponent == null) {
            Internal.supportsComponent = Reflection.hasClass("net.kyori.adventure.text.Component");
        }
        return Internal.supportsComponent;
    }

    private Internal() {
        throw new IllegalArgumentException("Utility class!");
    }
}
