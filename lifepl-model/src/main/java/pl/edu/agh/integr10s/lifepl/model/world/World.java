package pl.edu.agh.integr10s.lifepl.model.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.actor.ActorProfile;

import java.util.*;

public class World implements WorldModelView {

    private static final Logger logger = LoggerFactory.getLogger(World.class);

    private final String name;
    private String description;
    private Set<Actor> actors = new HashSet<>();
    private Set<Action> allowedActions = new HashSet<>();
    private Map<Action, List<ActionSlot>> actionsSlots = new HashMap<>();

    public World(String name) {
        this.name = name;
        setDescription(name + "-sample description");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        logger.info("set world {} description to \"{}\"", this, description);
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Override
    public Set<Action> getAllowedActions() {
        return Collections.unmodifiableSet(allowedActions);
    }

    @Override
    public List<ActionSlot> getActionSlots(Action action) {
        List<ActionSlot> actionSlots = actionsSlots.get(action);
        if (actionSlots == null) {
            logger.info("action {} has no configured slots inside world model {} ", action, this);
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(actionSlots);
    }

    public ActionSlot addActionSlot(Action action, ActionSlotConfig slotConfig) {
        logger.info("adding action slot with configuration {} for action {}", slotConfig, action);

        ActionSlot slot = slotConfig.createSlot(action);

        List<ActionSlot> actionSlots = actionsSlots.get(action);
        if (actionSlots == null) {
            logger.debug("action {} declaring it's first action slot {}", action, slot);
            actionSlots = new ArrayList<>();
            actionsSlots.put(action, actionSlots);
        }

        actionSlots.add(slot);

        return slot;
    }

    public void createActorFromProfile(ActorProfile actorProfile) {
        logger.info("add actor {} to world {}");
        Actor actor = actorProfile.createFor(this);

        if (!actors.add(actor)) {
            logger.debug("world {} was already contain actor {}", this, actor);
        }
    }

    public Set<Actor> getActors() {
        return Collections.unmodifiableSet(actors);
    }

    public void addAllowedAction(Action action) {
        logger.info("add allowed action {} to world model {} ", action, this);
        allowedActions.add(action);
    }

    @Override
    public int hashCode() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.World:: int hashCode ()
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.World:: boolean equals ()
        return super.equals(obj);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.World:: String toString ()
        return super.toString();
    }
}
