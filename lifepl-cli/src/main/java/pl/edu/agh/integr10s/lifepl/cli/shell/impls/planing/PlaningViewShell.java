package pl.edu.agh.integr10s.lifepl.cli.shell.impls.planing;

import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.engine.resolve.Planning;

class PlaningViewShell extends SpecializedSubShell {

    private final Planning planning;

    public PlaningViewShell(Planning planning) {
        super("planning-view", "View of chosen planning command line");
        this.planning = planning;
    }
}
