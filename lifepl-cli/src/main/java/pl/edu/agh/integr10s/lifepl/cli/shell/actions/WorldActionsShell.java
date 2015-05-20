package pl.edu.agh.integr10s.lifepl.cli.shell.actions;

import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public class WorldActionsShell extends SpecializedSubShell {
    private final World world;

    public WorldActionsShell(World world) {
        super("actions", "Actions editing command line");
        this.world = world;
    }

    //1. list actions
    //2. add action
    //3. remove action
}
