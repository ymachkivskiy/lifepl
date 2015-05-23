package pl.edu.agh.integr10s.lifepl.cli.shell;

import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.engine.resolve.EngineFactory;
import pl.edu.agh.integr10s.engine.resolve.PlanningEngines;
import pl.edu.agh.integr10s.lifepl.persistance.common.ActorsRepository;
import pl.edu.agh.integr10s.lifepl.persistance.common.PlaningRepository;
import pl.edu.agh.integr10s.lifepl.persistance.common.WorldsRepository;

import java.util.List;

public class ApplicationContext implements AppContext {

    private ActorsRepository actorsRepository;
    private WorldsRepository worldsRepository;
    private PlaningRepository planingRepository;
    private PlanningEngines planningEngines;

    public PlaningRepository getPlaningRepository() {
        return planingRepository;
    }

    public void setPlaningRepository(PlaningRepository planingRepository) {
        this.planingRepository = planingRepository;
    }

    public WorldsRepository getWorldsRepository() {
        return worldsRepository;
    }

    public void setWorldsRepository(WorldsRepository worldsRepository) {
        this.worldsRepository = worldsRepository;
    }

    public ActorsRepository getActorsRepository() {
        return actorsRepository;
    }

    public void setActorsRepository(ActorsRepository actorsRepository) {
        this.actorsRepository = actorsRepository;
    }

    public List<EngineFactory> getPlaningEnginesFactories() {
        return planningEngines.getEngineFactories();
    }

    public void setPlanningEngines(PlanningEngines planningEngines) {
        this.planningEngines = planningEngines;
    }
}
