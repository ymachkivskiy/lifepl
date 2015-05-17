package pl.edu.agh.integr10s.lifepl.model.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.capability.ActorCapabilities;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public class ActorProfile {

    private static final Logger logger = LoggerFactory.getLogger(ActorProfile.class);

    private String actorName;
    private ActorCapabilities actorCapabilities;

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setCapabilities(ActorCapabilities capabilities) {
        this.actorCapabilities = capabilities;
    }

    public Actor createFor(World world) {
        logger.debug("create actor for world {} from profile {}", world, this);
        return new Actor(actorName, world, actorCapabilities);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.actor.ActorProfile:: String toString ()
        return super.toString();
    }
}
