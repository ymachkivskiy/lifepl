package pl.edu.agh.integr10s.lifepl.cli.shell.planing;

import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;

class PlaningViewShell extends SpecializedSubShell<ApplicationContext> {

    private final Planning planning;

    public PlaningViewShell(Planning planning) {
        super("planning-view", "View of chosen planning command line");
        this.planning = planning;
    }
}
