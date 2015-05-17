package pl.edu.agh.integr10s.lifepl.model.actor.capability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotMembersCountRestriction;

public class SlotMembersCountCapability extends ActorCapability<SlotMembersCountRestriction> {
    private static final Logger logger = LoggerFactory.getLogger(SlotMembersCountCapability.class);

    public SlotMembersCountCapability() {
        super(SlotMembersCountRestriction.class);
    }

    @Override
    protected boolean canBeAppliedTo(SlotMembersCountRestriction slotRestriction) {
        return slotRestriction.canAddNewMember();
    }

    @Override
    protected void applyOnImpl(SlotMembersCountRestriction slotRestriction) {
        logger.debug("add actor {} to slot restriction {}", actor, slotRestriction);
        slotRestriction.addNewMember();
    }

    @Override
    protected void revokeOnImpl(SlotMembersCountRestriction slotRestriction) {
        logger.debug("remove actor {} from slot restriction {}", actor, slotRestriction);
        slotRestriction.removeMember();
    }

    @Override
    public String toString() {
        return "[Capability(Slot Members Count) for actor " + actor + "]";
    }
}
