package pl.edu.agh.integr10s.lifepl.model.world;

import org.apache.commons.lang3.builder.HashCodeBuilder;
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
        return new HashCodeBuilder(11, 197)
                .append(beginOfSlot)
                .append(endOfSlot)
                .append(action)
                .append(slotRestrictions)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ActionSlot) {
            ActionSlot other = (ActionSlot) obj;
            return     action.equals(other.action)
                    && beginOfSlot.equals(other.beginOfSlot)
                    && endOfSlot.equals(other.endOfSlot)
                    && slotRestrictions.equals(other.slotRestrictions);
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result
                .append("ActionSlot { \n")
                .append(" action : ").append(action).append("\n")
                .append(" begin : ").append(beginOfSlot).append("\n")
                .append(" end : ").append(endOfSlot).append("\n")
                .append(" restrictions : ").append(slotRestrictions).append("\n}");

        return result.toString();
    }

    public Action getAction() {
        return action;
    }
}
