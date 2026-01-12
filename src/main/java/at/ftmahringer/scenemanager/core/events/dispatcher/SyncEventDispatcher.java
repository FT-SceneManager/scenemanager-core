package at.ftmahringer.scenemanager.core.events.dispatcher;

import at.ftmahringer.scenemanager.api.events.*;

import java.util.List;

public class SyncEventDispatcher implements EventDispatcher {

    private final List<SceneManagerEventListener<?>> listeners;

    public SyncEventDispatcher(List<SceneManagerEventListener<?>> listeners) {
        this.listeners = listeners;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void dispatch(SceneManagerEvent event) {
        for (var listener : listeners) {
            ((SceneManagerEventListener<SceneManagerEvent>) listener).onEvent(event);
        }
    }
}
