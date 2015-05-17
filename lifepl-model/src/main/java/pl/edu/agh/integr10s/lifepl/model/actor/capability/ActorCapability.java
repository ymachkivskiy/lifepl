package pl.edu.agh.integr10s.lifepl.model.actor.capability;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;

import java.util.Optional;

public abstract class ActorCapability<SlotRestrictionT extends SlotRestriction> {
    private static final Logger logger = LoggerFactory.getLogger(ActorCapability.class);

    private final Class<SlotRestrictionT> applicablePropertyClass;

    private Optional<ActorCapability<? extends SlotRestriction>> nextDelegate = Optional.empty();
    protected Actor actor;

    public ActorCapability(Class<SlotRestrictionT> applicablePropertyClass) {
        this.applicablePropertyClass = applicablePropertyClass;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
        if (nextDelegate.isPresent()) {
            nextDelegate.get().setActor(actor);
        }
    }

    public final boolean applicableTo(SlotRestriction slotRestriction) {
        logger.debug("check if agent capability '{}' is applicable to slot restriction '{}'", this, slotRestriction);
        if (slotRestriction.getClass() == applicablePropertyClass) {
            logger.debug("agent capability is applicable", this, slotRestriction);
            return canBeAppliedTo(applicablePropertyClass.cast(slotRestriction));
        } else if (nextDelegate.isPresent()) {
            logger.debug("agent capability is not applicable, checking delegate capability", this, slotRestriction);
            return nextDelegate.get().applicableTo(slotRestriction);
        } else {
            logger.warn("slot restriction '{}' is not covered by any capability");
            return false;
        }
    }

    public final void applyOn(SlotRestriction slotRestriction) {
        logger.debug("apply capability '{}' on restriction '{}'", this, slotRestriction);
        if (slotRestriction.getClass() == applicablePropertyClass) {
            logger.debug("agent capability will apply on restriction");
            applyOnImpl(applicablePropertyClass.cast(slotRestriction));
            logger.debug("agent capability applied on restriction");
        } else if (nextDelegate.isPresent()) {
            logger.debug("agent capability is not applicable, will call on delegate");
            nextDelegate.get().applyOn(slotRestriction);
        } else {
            logger.warn("slot restriction '{}' is not covered by any capability");
        }
    }

    public final void revokeOn(SlotRestriction slotRestriction) {
        logger.debug("revoke capability '{}' on restriction '{}'", this, slotRestriction);
        if (slotRestriction.getClass() == applicablePropertyClass) {
            logger.debug("agent capability will be revoked on restriction");
            applyOnImpl(applicablePropertyClass.cast(slotRestriction));
            logger.debug("agent capability revoked on restriction");
        } else if (nextDelegate.isPresent()) {
            logger.debug("agent capability is not applicable, will call on delegate");
            nextDelegate.get().applyOn(slotRestriction);
        } else {
            logger.warn("slot restriction '{}' is not covered by any capability");
        }
    }

    public ActorCapability<? extends SlotRestriction> setNextDelegate(ActorCapability<? extends SlotRestriction> nextDelegate) {
        logger.debug("set delegate '{}' for '{}'", nextDelegate, this);
        this.nextDelegate = Optional.of(nextDelegate);
        return nextDelegate;
    }

    protected abstract void applyOnImpl(SlotRestrictionT slotRestriction);

    protected abstract boolean canBeAppliedTo(SlotRestrictionT slotRestriction);

    protected abstract void revokeOnImpl(SlotRestrictionT slotRestriction);

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 37)
            .append(applicablePropertyClass.hashCode())
            .append(actor.hashCode())
            .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ActorCapability) {
            ActorCapability other = (ActorCapability) obj;
            if (this.getClass() != obj.getClass()) {
                return false;
            }

            return actor.equals(other.actor);
        }

        return false;
    }

}
