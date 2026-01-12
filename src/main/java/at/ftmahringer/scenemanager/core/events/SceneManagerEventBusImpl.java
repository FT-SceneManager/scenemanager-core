package at.ftmahringer.scenemanager.core.events;

import at.ftmahringer.scenemanager.api.events.SceneManagerEvent;
import at.ftmahringer.scenemanager.api.events.SceneManagerEventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SceneManagerEventBusImpl {

    private final List<SceneManagerEventListener<?>> listeners = new CopyOnWriteArrayList<>();

    public <E extends SceneManagerEvent> void register(SceneManagerEventListener<E> listener) {
        if (listener == null) throw new IllegalArgumentException("listener must not be null");
        listeners.add(listener);
    }

    public void unregister(SceneManagerEventListener<?> listener) {
        listeners.remove(listener);
    }

    @SuppressWarnings("unchecked")
    public void fire(SceneManagerEvent event) {
        for (var listener : listeners) {
            try {
                ((SceneManagerEventListener<SceneManagerEvent>) listener).onEvent(event);
            } catch (Exception ignored) {
                // Listener should never kill the core
            }
        }
    }
}
