package pl.edu.agh.integr10s.lifepl.model.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActionSlotBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ActionSlotBuilder.class);

    private LocalDateTime slotStart = LocalDateTime.now();
    private LocalDateTime slotEnd = slotStart.plusHours(1);
    private Set<SlotRestriction> slotRestrictions = new HashSet<>();

    LocalDateTime getSlotStart() {
        return slotStart;
    }

    LocalDateTime getSlotEnd() {
        return slotEnd;
    }

    Set<SlotRestriction> getSlotRestrictions() {
        return slotRestrictions;
    }

    public void setSlotStart(LocalDateTime slotStart) {
        checkNotNull(slotStart);
        this.slotStart = slotStart;
    }

    public void setSlotEnd(LocalDateTime slotEnd) {
        checkNotNull(slotEnd);
        this.slotEnd = slotEnd;
    }

    public void addSlotProperty(SlotRestriction property) {
        logger.debug("adding slot property {} to slot builder {}", property, this);
        slotRestrictions.add(property);
    }

    public ActionSlot createSlot(Action action) {
        logger.debug("creating slot from configuration {} ", this);
        if (!slotStart.isBefore(slotEnd)) {
            logger.error("slot ending date {} is after it's start date {}", slotEnd, slotStart);
            return null;
        }

        return new ActionSlot(this, action);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.ActionSlotConfig:: String toString ()
        return super.toString();
    }
}
