package pl.edu.agh.integr10s.engine.resolve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanningEngines {

    private final List<EngineFactory> engineFactories = new ArrayList<>();

    public List<EngineFactory> getEngineFactories() {
        return Collections.unmodifiableList(engineFactories);
    }

    public void setEngineFactories(List<EngineFactory> factories) {
        engineFactories.addAll(factories);
    }
}
