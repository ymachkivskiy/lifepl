package pl.edu.agh.integr10s.lifepl.model.actor.capability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlot;

import java.util.HashSet;
import java.util.Set;

public class ActorCapabilities {

    private static final Logger logger = LoggerFactory.getLogger(ActorCapabilities.class);

    private Set<ActionSlot> joinedSlots = new HashSet<>();


    void setActor(Actor actor) {
        //TODO
    }

    public boolean capableToJoinSlot(ActionSlot actionSlot) {
        //TODO implement
        return false;
    }

    public void joinSlot(ActionSlot actionSlot) {
        //TODO
    }

    public void leaveSlot(ActionSlot actionSlot) {
        //TODO
    }
}
