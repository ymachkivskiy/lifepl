package pl.edu.agh.integr10s.engines.dummy;

import pl.edu.agh.integr10s.engine.factory.EngineDescription;
import pl.edu.agh.integr10s.engine.factory.EngineFactory;

public class DummyEngineFactory extends EngineFactory {
    private static final EngineDescription DUMMY_ENGINE_DESCRIPTION = new EngineDescription(
        "Dummy", "Does nothing", "NONE", "O(1)", "Very efficient =)"
    );

    protected DummyEngineFactory() {
        super(DUMMY_ENGINE_DESCRIPTION);
    }


    @Override
    public DummyEngine createEngine() {
        return new DummyEngine();
    }

}
