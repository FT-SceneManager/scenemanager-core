package at.ftmahringer.scenemanager.core.navigation;

import at.ftmahringer.scenemanager.api.scene.SceneKey;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class NavigationStack {

    private final Deque<SceneKey> stack = new ArrayDeque<>();

    void push(SceneKey key) {
        stack.push(key);
    }

    Optional<SceneKey> pop() {
        return stack.isEmpty()
                ? Optional.empty()
                : Optional.of(stack.pop());
    }

    Optional<SceneKey> peek() {
        return Optional.ofNullable(stack.peek());
    }

    boolean isEmpty() {
        return stack.isEmpty();
    }

    void clear() {
        stack.clear();
    }
}
