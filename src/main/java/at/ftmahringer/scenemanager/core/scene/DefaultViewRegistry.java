package at.ftmahringer.scenemanager.core.scene;

import at.ftmahringer.scenemanager.api.scene.SceneDescriptor;
import at.ftmahringer.scenemanager.api.scene.SceneKey;
import at.ftmahringer.scenemanager.api.scene.ViewRegistry;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultViewRegistry implements ViewRegistry {

    private final Map<SceneKey, SceneDescriptor> scenes = new ConcurrentHashMap<>();

    public void register(SceneDescriptor descriptor) {
        scenes.put(descriptor.key(), descriptor);
    }

    @Override
    public Optional<SceneDescriptor> get(SceneKey key) {
        return Optional.ofNullable(scenes.get(key));
    }

    @Override
    public boolean contains(SceneKey key) {
        return scenes.containsKey(key);
    }
}
