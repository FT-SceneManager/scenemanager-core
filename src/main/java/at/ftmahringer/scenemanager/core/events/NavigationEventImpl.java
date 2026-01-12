package at.ftmahringer.scenemanager.core.events;

import at.ftmahringer.scenemanager.api.events.NavigationEvent;
import at.ftmahringer.scenemanager.api.events.SceneManagerEvent;
import at.ftmahringer.scenemanager.api.navigation.NavigationMode;
import at.ftmahringer.scenemanager.api.scene.SceneKey;

public record NavigationEventImpl(
        SceneKey from,
        SceneKey to,
        NavigationMode mode
) implements NavigationEvent {
}
