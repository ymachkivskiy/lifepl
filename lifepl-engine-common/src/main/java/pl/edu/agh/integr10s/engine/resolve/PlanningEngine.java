package pl.edu.agh.integr10s.engine.resolve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.engine.factory.EngineFactory;

import java.time.LocalDateTime;

public abstract class PlanningEngine {
    private static final Logger logger = LoggerFactory.getLogger(PlanningEngine.class);
    private EngineFactory parentFactory;

    protected abstract PlanningResult resolveProblem(ProblemContext problemContext);

    public void setParentFactory(EngineFactory parentFactory) {
        this.parentFactory = parentFactory;
    }

    public Planning performPlaning(ProblemContext problemContext) {
        logger.info("start performing planing for problem context {}", problemContext);
        LocalDateTime startTime = LocalDateTime.now();

        PlanningResult result = resolveProblem(problemContext);

        LocalDateTime endTime = LocalDateTime.now();
        logger.info("finish performing planing for problem context {}", problemContext);

        return new Planning(result, parentFactory, problemContext, startTime, endTime);
    }
}
