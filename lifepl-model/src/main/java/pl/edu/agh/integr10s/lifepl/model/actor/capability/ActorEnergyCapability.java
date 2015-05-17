package pl.edu.agh.integr10s.lifepl.model.actor.capability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.EnergyRestriction;

public class ActorEnergyCapability extends ActorCapability<EnergyRestriction> {
    private static final Logger logger = LoggerFactory.getLogger(ActorEnergyCapability.class);

    private final int dayEnergyLimit;

    @Override
    protected void applyOnImpl(EnergyRestriction slotRestriction) {
        //TODO
    }

    @Override
    protected boolean canBeAppliedTo(EnergyRestriction slotRestriction) {
        //TODO
        return false;
    }

    @Override
    protected void revokeOnImpl(EnergyRestriction slotRestriction) {
        //TODO
    }

    public ActorEnergyCapability(int dayEnergyLimit) {
        super(EnergyRestriction.class);
        this.dayEnergyLimit = dayEnergyLimit;
    }

}
