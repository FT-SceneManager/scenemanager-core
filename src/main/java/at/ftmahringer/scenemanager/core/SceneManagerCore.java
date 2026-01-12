package at.ftmahringer.scenemanager.core;

import at.ftmahringer.scenemanager.api.events.SceneManagerEventListener;
import at.ftmahringer.scenemanager.api.navigation.NavigationMode;
import at.ftmahringer.scenemanager.api.navigation.NavigationRequest;
import at.ftmahringer.scenemanager.api.navigation.Navigator;
import at.ftmahringer.scenemanager.api.scene.SceneDescriptor;
import at.ftmahringer.scenemanager.api.scene.SceneKey;
import at.ftmahringer.scenemanager.api.state.NavigationState;
import at.ftmahringer.scenemanager.core.events.SceneManagerEventBusImpl;
import at.ftmahringer.scenemanager.core.lifecycle.LifecycleProcessor;
import at.ftmahringer.scenemanager.core.lifecycle.SceneStore;
import at.ftmahringer.scenemanager.core.navigation.DefaultNavigator;
import at.ftmahringer.scenemanager.core.navigation.NavigationStack;
import at.ftmahringer.scenemanager.core.scene.DefaultViewRegistry;
import at.ftmahringer.scenemanager.core.state.NavigationStateImpl;

/**
 * SceneManagerCore is single-threaded.
 * All calls must be made from the same thread.
 */
public final class SceneManagerCore {

    private final DefaultViewRegistry registry = new DefaultViewRegistry();
    private final NavigationStateImpl navigationState = new NavigationStateImpl();
    private final SceneManagerEventBusImpl eventBus = new SceneManagerEventBusImpl();
    private final NavigationStack stack = new NavigationStack();

    private SceneStore.SceneFactory factory;
    private LifecycleProcessor lifecycle;
    private Navigator navigator;

    public SceneManagerCore() {
        // init happens via bindSceneFactory()
    }

    public void bindSceneFactory(SceneStore.SceneFactory factory) {
        if (factory == null) throw new IllegalArgumentException("factory must not be null");

        this.factory = factory;
        this.lifecycle = new LifecycleProcessor(eventBus, factory);
        this.navigator = new DefaultNavigator(registry, stack, navigationState, lifecycle, eventBus);
    }

    public void start(SceneKey initialScene) {
        ensureReady();
        navigator.navigate(new NavigationRequest(initialScene, NavigationMode.PUSH));
    }

    public Navigator navigator() {
        ensureReady();
        return navigator;
    }

    public NavigationState state() {
        return navigationState;
    }

    public void registerScene(SceneDescriptor descriptor) {
        if (descriptor == null) throw new IllegalArgumentException("descriptor must not be null");
        registry.register(descriptor);
    }

    public void registerEventListener(SceneManagerEventListener<?> listener) {
        eventBus.register(listener);
    }

    public SceneManagerEventBusImpl events() {
        return eventBus;
    }

    private void ensureReady() {
        if (factory == null || lifecycle == null || navigator == null) {
            throw new IllegalStateException("SceneManagerCore not initialized. Call bindSceneFactory(...) first.");
        }
    }
}
