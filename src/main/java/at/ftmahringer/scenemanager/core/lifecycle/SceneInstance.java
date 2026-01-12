package at.ftmahringer.scenemanager.core.lifecycle;

import at.ftmahringer.scenemanager.api.lifecycle.InitializableView;
import at.ftmahringer.scenemanager.api.lifecycle.ResettableView;
import at.ftmahringer.scenemanager.api.lifecycle.ViewController;
import at.ftmahringer.scenemanager.api.scene.SceneKey;

final class SceneInstance {

    private final SceneKey key;
    private final ViewController controller;
    private boolean initialized = false;

    SceneInstance(SceneKey key, ViewController controller) {
        if (key == null) throw new IllegalArgumentException("key must not be null");
        if (controller == null) throw new IllegalArgumentException("controller must not be null");
        this.key = key;
        this.controller = controller;
    }

    SceneKey key() {
        return key;
    }

    ViewController controller() {
        return controller;
    }

    boolean isInitialized() {
        return initialized;
    }

    void initializeIfNeeded() {
        if (!initialized && controller instanceof InitializableView init) {
            init.onInitialize();
            initialized = true;
        }
    }

    void resetIfSupported() {
        if (controller instanceof ResettableView reset) {
            reset.onReset();
        }
    }
}
