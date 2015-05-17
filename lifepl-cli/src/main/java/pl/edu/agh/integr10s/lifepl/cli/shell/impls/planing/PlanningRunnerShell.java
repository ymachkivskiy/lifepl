package pl.edu.agh.integr10s.lifepl.cli.shell.impls.planing;


import asg.cliche.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.engine.resolve.EngineFactory;
import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.engine.resolve.PlanningEngine;
import pl.edu.agh.integr10s.engine.resolve.ProblemContext;
import pl.edu.agh.integr10s.lifepl.cli.props.ActorProperties;
import pl.edu.agh.integr10s.lifepl.cli.props.EngineProperties;
import pl.edu.agh.integr10s.lifepl.cli.props.WorldProperties;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.persistance.planing.PlaningService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PlanningRunnerShell extends SpecializedSubShell {
    private static final Logger logger = LoggerFactory.getLogger(PlanningRunnerShell.class);

    private final Listing<EngineFactory> enginesListing;
    private final Listing<World> worldsListing;
    private EngineFactory chosenEngineFactory;
    private World chosenWorld;

    private final PlaningService planingService;
    private final ProblemContext problemContext = new ProblemContext();

    public PlanningRunnerShell(ApplicationContext applicationContext) {
        super("new-planning", "Configure new planning simulation");

        List<EngineFactory> engineFactories = applicationContext.getPlaningEnginesFactories();
        enginesListing = Listing.For(engineFactories, EngineProperties.PROPERTY_EXTRACTOR);

        Collection<World> worlds = applicationContext.getWorldsService().getWorlds();
        worldsListing = Listing.For(worlds, WorldProperties.PROPERTY_EXTRACTOR);

        chosenWorld = worlds.iterator().next();
        chosenEngineFactory = engineFactories.get(0);

        planingService = applicationContext.getPlaningService();

        problemContext.setWorld(chosenWorld);
    }

    @Command(name = "set-engine", abbrev = "se", description = "Set planning engine")
    public void chooseEngine() {
        Optional<EngineFactory> newEngineCandidate = enginesListing.choose();
        if (newEngineCandidate.isPresent()) {
            EngineFactory engineFactory = newEngineCandidate.get();
            logger.info("set {} as planning engine", engineFactory.getEngineDescription().getName());
            chosenEngineFactory = engineFactory;
        }
    }

    @Command(name = "set-world", abbrev = "sw", description = "Set world model for planning")
    public void chooseWorldModel() {
        Optional<World> newWorldModelCandidate = worldsListing.choose();
        if (newWorldModelCandidate.isPresent()) {
            final World worldModel = newWorldModelCandidate.get();
            logger.info("set {} as world model", worldModel.getName());
            chosenWorld = worldModel;
        }
    }

    @Command(name = "set-actors", abbrev = "sa", description = "Set actors for planning from chosen world model")
    public void chooseActorsFromWorldModel() {
        logger.info("choose actors from current world model for planning");

        Listing<Actor> actorsListing = Listing.For(chosenWorld.getActors(), ActorProperties.PROPERTY_EXTRACTOR_WORLD);
        Collection<Actor> chosenActors = actorsListing.chooseMany();
        if (!chosenActors.isEmpty()) {
            problemContext.addActors(chosenActors);
        }
    }

    @Command(name = "show", abbrev = "s", description = "Show current planning settings")
    public void showCurrentSettings() {
        StringBuilder currentSettings = new StringBuilder();

        currentSettings.append(getCurrentEngineSettings());
        currentSettings.append("\n");
        currentSettings.append(getCurrentWorldWithAgentsSettings());

        System.out.println(currentSettings.toString());
    }

    private String getCurrentWorldWithAgentsSettings() {
        StringBuilder result = new StringBuilder();

        result
            .append("Current World : ")
            .append(chosenWorld.getName())
            .append(" [")
            .append(chosenWorld.getDescription())
            .append("]\n");

        result.append("Actors :\n");

        for (Actor actor : problemContext.getProblemActors()) {
            result.append(" * ").append(actor.getName()).append("\n");
        }

        return result.toString();
    }

    private String getCurrentEngineSettings() {
        return "Current Engine : " + chosenEngineFactory.getEngineDescription().getName() + " [" + chosenEngineFactory.getEngineDescription().getDescription() + "]\n";
    }

    @Command(name = "run-planning", abbrev = "run", description = "Run planning for current settings")
    public void runPlanning() {
        logger.info("start planning for world {} with engine {}", chosenWorld.getName(), chosenEngineFactory.getEngineDescription().getName());

        PlanningEngine engine = chosenEngineFactory.newEngine();
        Planning planning = engine.performPlaning(problemContext);

        logger.info("finish planning for world {} with engine {}", chosenWorld.getName(), chosenEngineFactory.getEngineDescription().getName());

        planingService.addPlaning(planning);
    }
}
