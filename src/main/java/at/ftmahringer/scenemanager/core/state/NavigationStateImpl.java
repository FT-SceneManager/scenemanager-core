package at.ftmahringer.scenemanager.core.state;

import at.ftmahringer.scenemanager.api.scene.SceneKey;
import at.ftmahringer.scenemanager.api.state.NavigationState;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class NavigationStateImpl implements NavigationState {

    private final AtomicReference<SceneKey> current = new AtomicReference<>();

    public void setCurrent(SceneKey key) {
        current.set(key);
    }

    @Override
    public Optional<SceneKey> currentScene() {
        return Optional.ofNullable(current.get());
    }
}
