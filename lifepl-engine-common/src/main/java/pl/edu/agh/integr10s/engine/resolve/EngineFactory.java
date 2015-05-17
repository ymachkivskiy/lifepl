package pl.edu.agh.integr10s.engine.resolve;

public abstract class EngineFactory {

    private final EngineDescription engineDescription;

    protected EngineFactory(EngineDescription engineDescription) {
        this.engineDescription = engineDescription;
    }

    protected abstract PlanningEngine createEngine();

    public final EngineDescription getEngineDescription() {
        return engineDescription;
    }

    public PlanningEngine newEngine() {
        PlanningEngine e = createEngine();
        e.setParentFactory(this);
        return e;
    }
}
