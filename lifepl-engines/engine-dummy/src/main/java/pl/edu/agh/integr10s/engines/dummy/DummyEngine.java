package pl.edu.agh.integr10s.engines.dummy;

import pl.edu.agh.integr10s.engine.resolve.PlanningEngine;
import pl.edu.agh.integr10s.engine.resolve.PlanningResult;
import pl.edu.agh.integr10s.engine.resolve.ProblemContext;

public class DummyEngine extends PlanningEngine {
    @Override
    protected PlanningResult resolveProblem(ProblemContext problemContext) {
        return null;
    }
}
