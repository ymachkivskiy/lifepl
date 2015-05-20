package pl.edu.agh.integr10s.lifepl.cli.shell.planing;

import asg.cliche.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.engine.resolve.EngineFactory;
import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.lifepl.cli.props.EngineProperties;
import pl.edu.agh.integr10s.lifepl.cli.props.PlaningProperties;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;

import java.util.Optional;

public class PlaningShell extends CategorizedShell<ShellName, ApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(PlaningShell.class);

    public PlaningShell() {
        super(ShellName.class, ShellName.PLANING, ShellName.MAIN);
    }

    @Command(name = "list-plannings", abbrev = "lsp", description = "List all performed plannings")
    public void listPerformedPlannings() {
        Listing<Planning> listing = Listing.For(getApplicationState().getPlaningService().getAllPlannings(), PlaningProperties.PROPERTY_EXTRACTOR);
        listing.list();
    }

    @Command(name = "list-engines", abbrev = "lse", description = "List available engines")
    public void listAvailableEngines() {
        Listing<EngineFactory> listing = Listing.For(getApplicationState().getPlaningEnginesFactories(), EngineProperties.PROPERTY_EXTRACTOR);
        listing.list();
    }

    @Command(name = "new-planning", abbrev = "np", description = "Create new planning")
    public void newSimulation() {
        final ApplicationContext applicationState = getApplicationState();

        if (applicationState.getPlaningEnginesFactories().isEmpty()) {
            logger.error("Planning can not be performed : no planning engine available");
        }else if(applicationState.getWorldsService().getWorlds().isEmpty()){
            logger.error("Planning can not be performed : no world model available");
        }else{
            runSpecializedShell(new PlanningRunnerShell(applicationState));
        }
    }

    @Command(name = "view-planning", abbrev = "vp", description = "View chosen planing results")
    public void viewPlanning() {
        Listing<Planning> planningListing = Listing.For(getApplicationState().getPlaningService().getAllPlannings(), PlaningProperties.PROPERTY_EXTRACTOR);
        Optional<Planning> chosenPlanning = planningListing.choose();
        if (chosenPlanning.isPresent()) {
            final Planning planning = chosenPlanning.get();
            logger.info("view planing results for chosen planing {}", planning);
            runSpecializedShell(new PlaningViewShell(planning));
        }
    }

}
