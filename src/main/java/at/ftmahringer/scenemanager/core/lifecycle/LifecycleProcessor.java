package at.ftmahringer.scenemanager.core.lifecycle;

import at.ftmahringer.scenemanager.api.events.SceneManagerEventType;
import at.ftmahringer.scenemanager.api.scene.SceneKey;
import at.ftmahringer.scenemanager.core.events.SceneLifecycleEventImpl;
import at.ftmahringer.scenemanager.core.events.SceneManagerEventBusImpl;

public final class LifecycleProcessor {

    private final SceneStore store = new SceneStore();
    private final SceneManagerEventBusImpl eventBus;
    private final SceneStore.SceneFactory factory;

    private SceneInstance current;

    public LifecycleProcessor(SceneManagerEventBusImpl eventBus,
                              SceneStore.SceneFactory factory) {
        if (eventBus == null) throw new IllegalArgumentException("eventBus must not be null");
        if (factory == null) throw new IllegalArgumentException("factory must not be null");
        this.eventBus = eventBus;
        this.factory = factory;
    }

    public void handleTransition(SceneKey from, SceneKey to) {

        // If nothing changes -> no-op
        if ((from == null && to == null) || (from != null && from.equals(to))) {
            return;
        }

        // --- LEAVING SCENE ---
        if (from != null && current != null) {
            eventBus.fire(new SceneLifecycleEventImpl(SceneManagerEventType.SCENE_HIDDEN, from));

            current.resetIfSupported();
            eventBus.fire(new SceneLifecycleEventImpl(SceneManagerEventType.SCENE_RESET, from));
        }

        // Navigated to "no scene"
        if (to == null) {
            current = null;
            return;
        }

        // --- ENTERING SCENE ---
        SceneInstance next = store.getOrCreate(to, factory);

        if (!next.isInitialized()) {
            eventBus.fire(new SceneLifecycleEventImpl(SceneManagerEventType.SCENE_CREATED, to));

            next.initializeIfNeeded();
            eventBus.fire(new SceneLifecycleEventImpl(SceneManagerEventType.SCENE_INITIALIZED, to));
        }

        current = next;

        eventBus.fire(new SceneLifecycleEventImpl(SceneManagerEventType.SCENE_SHOWN, to));
    }
}
