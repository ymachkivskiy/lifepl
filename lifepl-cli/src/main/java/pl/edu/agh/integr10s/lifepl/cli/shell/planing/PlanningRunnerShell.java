package pl.edu.agh.integr10s.lifepl.cli.shell.planing;


import asg.cliche.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.engine.resolve.EngineFactory;
import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.persistence.common.PlaningRepository;

import java.util.Collection;
import java.util.Optional;

public class PlanningRunnerShell extends SpecializedSubShell<ApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(PlanningRunnerShell.class);

    private final PlaningRepository planingRepository;
    private final PlaningConfiguration planingConfiguration;

    public PlanningRunnerShell(ApplicationContext applicationContext) {
        super("new-planning", "Configure new planning simulation");

        planingConfiguration = new PlaningConfiguration(applicationContext);
        planingRepository = applicationContext.getPlaningRepository();
    }

    @Command(name = "set-engine", abbrev = "se", description = "Set planning engine")
    public void chooseEngine() {
        Optional<EngineFactory> newEngineCandidate = planingConfiguration.engineFactoryListing().choose();
        if (newEngineCandidate.isPresent()) {
            EngineFactory engineFactory = newEngineCandidate.get();
            logger.info("set {} as planning engine", engineFactory.getEngineDescription().getName());
            planingConfiguration.setEngineFactory(engineFactory);
        }
    }

    @Command(name = "set-world", abbrev = "sw", description = "Set world model for planning")
    public void chooseWorldModel() {
        Optional<World> newWorldModelCandidate = planingConfiguration.worldListing().choose();
        if (newWorldModelCandidate.isPresent()) {
            final World worldModel = newWorldModelCandidate.get();
            logger.info("set {} as world model", worldModel.getName());
            planingConfiguration.setWorld(worldModel);
        }
    }

    @Command(name = "set-actors", abbrev = "sa", description = "Set actors for planning from chosen world model")
    public void chooseActorsFromWorldModel() {
        logger.info("choose actors from current world model for planning");

        Collection<Actor> chosenActors = planingConfiguration.actorListing().chooseMany();
        if (!chosenActors.isEmpty()) {
            planingConfiguration.addActors(chosenActors);
        }
    }

    @Command(name = "show", abbrev = "s", description = "Show current planning settings")
    public void showCurrentSettings() {
        planingConfiguration.createListing().listWithoutNumbering();
    }


    @Command(name = "run-planning", abbrev = "run", description = "Run planning for current settings")
    public void runPlanning() {
        logger.info("start planning");
        Planning planning = planingConfiguration.performPlanning();
        logger.info("finish planning");
        planingRepository.addPlaning(planning);
    }
}
