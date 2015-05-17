package pl.edu.agh.integr10s.engine.resolve;

import java.time.LocalDateTime;

public class Planning {
    private final PlanningResult result;
    private final EngineFactory executiveEngineClass;
    private final ProblemContext problemContext;
    private final LocalDateTime planingBeginTime;
    private final LocalDateTime planingEndTime;

    Planning(PlanningResult result, EngineFactory executiveEngineClass, ProblemContext problemContext, LocalDateTime planingBeginTime, LocalDateTime planingEndTime) {
        this.result = result;
        this.executiveEngineClass = executiveEngineClass;
        this.problemContext = problemContext;
        this.planingBeginTime = planingBeginTime;
        this.planingEndTime = planingEndTime;
    }

    public PlanningResult getResult() {
        return result;
    }

    public EngineFactory getExecutiveEngineClass() {
        return executiveEngineClass;
    }

    public ProblemContext getProblemContext() {
        return problemContext;
    }

    public LocalDateTime getPlaningBeginTime() {
        return planingBeginTime;
    }

    public LocalDateTime getPlaningEndTime() {
        return planingEndTime;
    }
}
