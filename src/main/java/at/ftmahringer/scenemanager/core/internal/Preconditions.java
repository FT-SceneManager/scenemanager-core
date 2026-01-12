package at.ftmahringer.scenemanager.core.internal;

public final class Preconditions {

    private Preconditions() {}

    public static void notNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
