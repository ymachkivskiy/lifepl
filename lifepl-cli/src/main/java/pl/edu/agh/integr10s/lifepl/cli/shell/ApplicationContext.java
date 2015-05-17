package pl.edu.agh.integr10s.lifepl.cli.shell;

import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.engine.resolve.EngineFactory;
import pl.edu.agh.integr10s.engine.resolve.PlanningEngines;
import pl.edu.agh.integr10s.lifepl.persistance.actors.ActorsService;
import pl.edu.agh.integr10s.lifepl.persistance.planing.PlaningService;
import pl.edu.agh.integr10s.lifepl.persistance.worlds.WorldsService;

import java.util.List;

public class ApplicationContext implements AppContext {

    private ActorsService actorsService;
    private WorldsService worldsService;
    private PlaningService planingService;
    private PlanningEngines planningEngines;

    public PlaningService getPlaningService() {
        return planingService;
    }

    public void setPlaningService(PlaningService planingService) {
        this.planingService = planingService;
    }

    public WorldsService getWorldsService() {
        return worldsService;
    }

    public void setWorldsService(WorldsService worldsService) {
        this.worldsService = worldsService;
    }

    public ActorsService getActorsService() {
        return actorsService;
    }

    public void setActorsService(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    public List<EngineFactory> getPlaningEnginesFactories() {
        return planningEngines.getEngineFactories();
    }

    public void setPlanningEngines(PlanningEngines planningEngines) {
        this.planningEngines = planningEngines;
    }
}
