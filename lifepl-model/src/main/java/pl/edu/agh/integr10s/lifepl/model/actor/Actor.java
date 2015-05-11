package pl.edu.agh.integr10s.lifepl.model.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.world.World;

import java.time.LocalDateTime;
import java.util.*;

public class Actor {

    private static final Logger logger = LoggerFactory.getLogger(Actor.class);

    private final String name;
    private final World world;
    private Set<Goal> goals = new HashSet<>();

    Actor(String name, World world) {
        this.name = name;
        this.world = world;
    }

    public void addGoal(Goal goal) {
        logger.info("add track {} to actor {} goals list", goal, this);
        goals.add(goal);
    }

    public Set<Goal> getGoals() {
        return Collections.unmodifiableSet(goals);
    }

    public World getWorld() {
        return world;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        //TODO implement pl.edu.agh.integr10s.lifepl.model.actor.Actor::hashCode
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO implement pl.edu.agh.integr10s.lifepl.model.actor.Actor::equals
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "actor::" + name;
    }

    public static Collection<Actor> sampleActors() {
        List<Actor> r = new ArrayList<>();
        //TODO -------- remove it
        final World world1 = new World("world-simple", LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        r.add(new Actor("Wiktor", world1));
        r.add(new Actor("Alex", world1));
        r.add(new Actor("Zbyszek", new World("world-2", LocalDateTime.now(), LocalDateTime.now().plusDays(3))));
        //-------------------

        return r;
    }
}
