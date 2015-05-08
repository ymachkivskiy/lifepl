package pl.edu.agh.integr10s.lifepl.model.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor {

    private static final Logger logger = LoggerFactory.getLogger(Actor.class);

    private final String name;
    private ActorProfile profile;

    public Actor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ActorProfile getProfile() {
        return profile;
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
}
