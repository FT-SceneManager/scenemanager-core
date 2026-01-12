package at.ftmahringer.scenemanager.core.scene;

import at.ftmahringer.scenemanager.api.scene.SceneDescriptor;
import at.ftmahringer.scenemanager.api.scene.SceneKey;

public record DefaultSceneDescriptor(
        SceneKey key,
        String title
) implements SceneDescriptor {
}
