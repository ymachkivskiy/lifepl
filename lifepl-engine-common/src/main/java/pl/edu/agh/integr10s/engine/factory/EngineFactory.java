package pl.edu.agh.integr10s.engine.factory;

import pl.edu.agh.integr10s.engine.resolve.PlanningEngine;

public interface EngineFactory {

    public PlanningEngine createEngine();

    public EngineDescription getEngineDescription();
}
