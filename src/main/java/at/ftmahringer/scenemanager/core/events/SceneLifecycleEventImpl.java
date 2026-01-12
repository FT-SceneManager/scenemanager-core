package at.ftmahringer.scenemanager.core.events;

import at.ftmahringer.scenemanager.api.events.SceneLifecycleEvent;
import at.ftmahringer.scenemanager.api.events.SceneManagerEventType;
import at.ftmahringer.scenemanager.api.scene.SceneKey;

public record SceneLifecycleEventImpl(
        SceneManagerEventType type,
        SceneKey sceneKey
) implements SceneLifecycleEvent {
}
