package pl.edu.agh.integr10s.lifepl.cli.shell.actions;

import asg.cliche.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.lifepl.cli.props.ActionProperties;
import pl.edu.agh.integr10s.lifepl.cli.props.ActionSlotProperties;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;
import pl.edu.agh.integr10s.lifepl.model.world.Action;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlot;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlotBuilder;
import pl.edu.agh.integr10s.lifepl.model.world.World;

import java.util.List;
import java.util.Optional;

public class WorldActionSlotEditShell extends SpecializedSubShell<ApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(WorldActionSlotEditShell.class);

    private final World world;

    public WorldActionSlotEditShell(World world) {
        super("action-slots", "Actions slots editing command line");
        this.world = world;
    }

    private Optional<Listing<ActionSlot>> getActionSlotListing() {
        logger.debug("listing action slots for chosen action inside world {}", world);
        Optional<Action> action = Listing.For(world.getAllowedActions(), ActionSlotProperties.SlotsNumberForActionExtractor(world)).choose();
        if (action.isPresent()) {
            List<ActionSlot> actionSlots = world.getActionSlots(action.get());
            return Optional.of(Listing.For(actionSlots, ActionSlotProperties.PROPERTY_EXTRACTOR));
        }
        logger.debug("no action was chosen");
        return Optional.empty();
    }

    @Command(name = "list-slots", abbrev = "ls", description = "List all action slots defined inside world model")
    public void listActionSlots() {
        Optional<Listing<ActionSlot>> listing = getActionSlotListing();
        if (listing.isPresent()) {
            listing.get().list();
        }
    }

    @Command(name = "new-slot", abbrev = "new", description = "Create new slot for action")
    public void addActionSlot() {

        logger.info("create new action slot");

        Optional<Action> action = Listing.For(world.getAllowedActions(), ActionProperties.PROPERTY_EXTRACTOR).choose();
        if (action.isPresent()) {
            final ActionSlotCreatorWizard wizard = new ActionSlotCreatorWizard(getApplicationState().getRestrictionsFabric());
            Optional<ActionSlotBuilder> newSlot = wizard.newSlotBuilder();
            if (newSlot.isPresent()) {
                world.addActionSlot(action.get(), newSlot.get());
            } else {
                logger.warn("action slot creation was canceled");
            }
        } else {
            logger.warn("no action chosen for slot creation, abort slot creation");
        }

    }

    @Command( name = "remove-slot", abbrev = "rm", description = "Remove chosen action slot from world model")
    public void removeActionSlot() {
        Optional<Listing<ActionSlot>> listing = getActionSlotListing();
        if (listing.isPresent()) {
            Optional<ActionSlot> actionSlot = listing.get().choose();
            if (actionSlot.isPresent()) {
                logger.debug("removing action slot {} from world {}", actionSlot, world);
                world.removeActionSlot(actionSlot.get());
            }
        }
    }
}
