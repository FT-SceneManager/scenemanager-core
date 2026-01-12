package at.ftmahringer.scenemanager.core.lifecycle;

import at.ftmahringer.scenemanager.api.lifecycle.ViewController;
import at.ftmahringer.scenemanager.api.scene.SceneKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SceneStore {

    private final Map<SceneKey, SceneInstance> scenes = new ConcurrentHashMap<>();

    SceneInstance getOrCreate(SceneKey key, SceneFactory factory) {
        return scenes.computeIfAbsent(key, k ->
                new SceneInstance(k, factory.create(k))
        );
    }

    public interface SceneFactory {
        ViewController create(SceneKey key);
    }
}
