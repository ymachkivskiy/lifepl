package pl.edu.agh.integr10s.engine.resolve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.Action;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlot;
import pl.edu.agh.integr10s.lifepl.model.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProblemContext {
    private static final Logger logger = LoggerFactory.getLogger(ProblemContext.class);

    private World world;

    private ArrayList<Actor> problemActors = new ArrayList<>();


    public List<Actor> getProblemActors() {
        return Collections.unmodifiableList(problemActors);
    }

    public List<ActionSlot> getActionSlots(Action action) {
        return Collections.emptyList();//TODO
    }

    public void setWorld(World world) {
        this.world = world;
        problemActors.clear();
    }

    public void addActors(Collection<Actor> actors) {
        for (Actor actor : actors) {
            addActor(actor);
        }
    }

    public void addActor(Actor actor) {
        logger.debug("add actor {} to problem context", actor);
        if (world.getActors().contains(actor)) {
            problemActors.add(actor);
        } else {
            logger.error("try to add actor which does not below to current world {}", world);
        }
    }


}
