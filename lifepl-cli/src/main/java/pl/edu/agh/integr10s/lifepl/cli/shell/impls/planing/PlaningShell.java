package pl.edu.agh.integr10s.lifepl.cli.shell.impls.planing;

import asg.cliche.Command;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.engine.factory.EngineFactory;
import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.lifepl.cli.props.EngineProperties;
import pl.edu.agh.integr10s.lifepl.cli.props.PlaningProperties;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;

public class PlaningShell extends CategorizedShell<ShellName, ApplicationContext> {
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

    public void viewSimulation() {
        //TODO choose one of done simulations and go inside it's view shell
    }

    public void newSimulation() {
        /* TODO go to new simulation sub shell

        1. engine should be choosen
        2. world should be choosen
        3. actors inside world should be choosen
        4. simulation run

    */
    }
}
