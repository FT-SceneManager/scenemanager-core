package at.ftmahringer.scenemanager.core.navigation;

import at.ftmahringer.scenemanager.api.exception.SceneNotFoundException;
import at.ftmahringer.scenemanager.api.navigation.*;
import at.ftmahringer.scenemanager.api.scene.SceneKey;
import at.ftmahringer.scenemanager.api.scene.ViewRegistry;
import at.ftmahringer.scenemanager.core.events.NavigationEventImpl;
import at.ftmahringer.scenemanager.core.events.SceneManagerEventBusImpl;
import at.ftmahringer.scenemanager.core.lifecycle.LifecycleProcessor;
import at.ftmahringer.scenemanager.core.state.NavigationStateImpl;

public final class DefaultNavigator implements Navigator {

    private final ViewRegistry registry;
    private final NavigationStack stack;
    private final NavigationStateImpl state;
    private final LifecycleProcessor lifecycle;
    private final SceneManagerEventBusImpl eventBus;

    public DefaultNavigator(
            ViewRegistry registry,
            NavigationStack stack,
            NavigationStateImpl state,
            LifecycleProcessor lifecycle,
            SceneManagerEventBusImpl eventBus
    ) {
        this.registry = registry;
        this.stack = stack;
        this.state = state;
        this.lifecycle = lifecycle;
        this.eventBus = eventBus;
    }

    @Override
    public void navigate(NavigationRequest request) {
        SceneKey target = request.target();
        NavigationMode mode = request.mode();

        if (mode == NavigationMode.BACK && stack.isEmpty()) {
            return; // no-op
        }

        if (mode != NavigationMode.BACK && !registry.contains(target)) {
            throw new SceneNotFoundException(target);
        }

        SceneKey from = stack.peek().orElse(null);
        SceneKey to;

        switch (mode) {
            case PUSH -> {
                stack.push(target);
                to = target;
            }
            case REPLACE -> {
                stack.pop();
                stack.push(target);
                to = target;
            }
            case BACK -> {
                stack.pop();
                to = stack.peek().orElse(null);
            }
            default -> throw new IllegalStateException("Unexpected mode: " + mode);
        }

        if ((from == null && to == null) || (from != null && from.equals(to))) {
            return;
        }

        // State zuerst, damit Listener bei SCENE_SHOWN schon konsistent sind
        state.setCurrent(to);

        // Navigation-Event (super für Logging/Debug später)
        eventBus.fire(new NavigationEventImpl(from, to, mode));

        // Lifecycle + Lifecycle-Events
        lifecycle.handleTransition(from, to);
    }
}
