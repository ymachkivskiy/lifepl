package pl.edu.agh.integr10s.lifepl.cli.shell.actions;

import asg.cliche.Command;
import asg.cliche.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.lifepl.cli.props.ActionProperties;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;
import pl.edu.agh.integr10s.lifepl.model.world.Action;
import pl.edu.agh.integr10s.lifepl.model.world.World;

import java.util.Optional;

public class WorldActionsShell extends SpecializedSubShell {
    private static final Logger logger = LoggerFactory.getLogger(WorldActionsShell.class);

    private final World world;

    public WorldActionsShell(World world) {
        super("actions", "Actions editing command line");
        this.world = world;
    }

    @Command(name = "list-actions", abbrev = "ls", description = "List all allowed actions for current world")
    public void listActions() {
        Listing.For(world.getAllowedActions(), ActionProperties.PROPERTY_EXTRACTOR).list();
    }

    @Command(name="add-action", abbrev = "add", description = "Add new allowed action to current world")
    public void addAction(@Param(name = "action_name") String actionName) {
        Action action = new Action(actionName);
        logger.info("add new action {} to world {} ", action, world);
        world.addAllowedAction(action);
    }

    @Command(name = "remove-action", abbrev = "rm", description = "Remove action from current world")
    public void removeAction() {
        Optional<Action> actionToRemove = Listing.For(world.getAllowedActions(), ActionProperties.PROPERTY_EXTRACTOR).choose();
        if (actionToRemove.isPresent()) {
            final Action action = actionToRemove.get();

            logger.info("removing action {} from world {}", action, world);
            world.removeAllowedAction(action);
        }
    }
}
