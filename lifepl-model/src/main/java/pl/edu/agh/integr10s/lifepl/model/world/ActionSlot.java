package pl.edu.agh.integr10s.lifepl.model.world;

import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public final class ActionSlot {

    private final LocalDateTime beginOfSlot;
    private final LocalDateTime endOfSlot;
    private final Action action;
    private final Set<SlotRestriction> slotRestrictions;

    ActionSlot(ActionSlotBuilder slotBuilder, Action action) {
        this.action = action;
        this.beginOfSlot = slotBuilder.getSlotStart();
        this.endOfSlot = slotBuilder.getSlotEnd();
        this.slotRestrictions = Collections.unmodifiableSet(slotBuilder.getSlotRestrictions());
    }

    public LocalDateTime getBeginOfSlot() {
        return beginOfSlot;
    }

    public LocalDateTime getEndOfSlot() {
        return endOfSlot;
    }


    public boolean hasNoRestrictions() {
        return slotRestrictions.isEmpty();
    }

    public Set<SlotRestriction> getRestrictions() {
        return slotRestrictions;
    }

    @Override
    public int hashCode() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.ActionSlot:: int hashCode ()
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.ActionSlot:: boolean equals ()
        return super.equals(obj);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.ActionSlot:: String toString ()
        return super.toString();
    }

    public Action getAction() {
        return action;
    }
}
