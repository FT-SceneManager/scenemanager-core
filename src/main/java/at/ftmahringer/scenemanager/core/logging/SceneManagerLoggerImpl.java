package at.ftmahringer.scenemanager.core.logging;

import at.ftmahringer.scenemanager.api.events.SceneManagerEvent;

public class SceneManagerLoggerImpl {

    public void log(SceneManagerEvent event) {
        System.out.println("[SceneManager] " + event);
    }
}
