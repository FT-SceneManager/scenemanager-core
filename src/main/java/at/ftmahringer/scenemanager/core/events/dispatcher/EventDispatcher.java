package at.ftmahringer.scenemanager.core.events.dispatcher;

import at.ftmahringer.scenemanager.api.events.SceneManagerEvent;

public interface EventDispatcher {
    void dispatch(SceneManagerEvent event);
}
