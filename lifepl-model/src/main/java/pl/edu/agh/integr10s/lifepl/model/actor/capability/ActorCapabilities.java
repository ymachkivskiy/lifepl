package pl.edu.agh.integr10s.lifepl.model.actor.capability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlot;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ActorCapabilities {

    private static final Logger logger = LoggerFactory.getLogger(ActorCapabilities.class);

    private Set<ActionSlot> joinedSlots = new HashSet<>();
    private Optional<ActorCapability<? extends SlotRestriction>> rootCapability = Optional.empty();
    private Actor actor;

    public void setActor(Actor actor) {
        logger.debug("set actor to {}", actor);
        this.actor = actor;
        if (rootCapability.isPresent()) {
            rootCapability.get().setActor(actor);
        } else {
            logger.debug("no capabilities found");
        }
    }

    public boolean capableToJoinSlot(ActionSlot actionSlot) {
        logger.info("check if actor {} capable to join slot {}", actor, actionSlot);
        if (joinedSlots.contains(actionSlot)) {
            logger.debug("actor {} already joined slot {}", actor, actionSlot);
            return true;
        }

        if (actionSlot.hasNoRestrictions()) {
            logger.debug("action slot has no restrictions, so agent can join to it");
            return true;
        }

        logger.debug("checking actor {} capabilities", actor);

        if (rootCapability.isPresent()) {
            ActorCapability<? extends SlotRestriction> rc = rootCapability.get();

            for (SlotRestriction slotRestriction : actionSlot.getRestrictions()) {
                if (!rc.applicableTo(slotRestriction)) return false;
            }

            logger.debug("actor has all capabilities to join slot");

            return true;
        }

        return false;
    }

    public void joinSlot(ActionSlot actionSlot) {
        logger.info("actor {} joining slot {}", actor, actionSlot);

        if (joinedSlots.contains(actionSlot)) {
            logger.warn("actor {} try to join slot {} which he has already joined", actor, actionSlot);
            return;
        }

        if (rootCapability.isPresent()) {
            ActorCapability<? extends SlotRestriction> rc = rootCapability.get();

            for (SlotRestriction slotRestriction : actionSlot.getRestrictions()) {
                rc.applyOn(slotRestriction);
            }

        }

        joinedSlots.add(actionSlot);
    }

    public void leaveSlot(ActionSlot actionSlot) {
        logger.info("actor {} leaving slot {}", actor, actionSlot);

        if (!joinedSlots.contains(actionSlot)) {
            logger.warn("actor {} try to leave slot {} which he has not joined", actor, actionSlot);
            return;
        }

        if (rootCapability.isPresent()) {
            ActorCapability<? extends SlotRestriction> rc = rootCapability.get();

            for (SlotRestriction slotRestriction : actionSlot.getRestrictions()) {
                rc.revokeOn(slotRestriction);
            }

        }

        joinedSlots.remove(actionSlot);
    }

    public ActorCapabilities addCapability(ActorCapability<? extends SlotRestriction> capability) {
        logger.debug("add capability {}", capability);

        if (rootCapability.isPresent()) {
            capability.setNextDelegate(rootCapability.get());
        }
        rootCapability = Optional.of(capability);

        return this;
    }

    private void removeJoinedSlot(ActionSlot slot) {
        joinedSlots.remove(slot);
    }
}
