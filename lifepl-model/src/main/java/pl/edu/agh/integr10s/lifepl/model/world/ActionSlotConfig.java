package pl.edu.agh.integr10s.lifepl.model.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActionSlotConfig {

    private static final Logger logger = LoggerFactory.getLogger(ActionSlotConfig.class);

    private LocalDateTime slotStart = LocalDateTime.now();
    private LocalDateTime slotEnd = slotStart;

    public LocalDateTime getSlotStart() {
        return slotStart;
    }

    public void setSlotStart(LocalDateTime slotStart) {
        checkNotNull(slotStart);
        this.slotStart = slotStart;
    }

    public LocalDateTime getSlotEnd() {
        return slotEnd;
    }

    public void setSlotEnd(LocalDateTime slotEnd) {
        checkNotNull(slotEnd);
        this.slotEnd = slotEnd;
    }

    public ActionSlot createSlot(Action action) {
        logger.debug("creating slot from configuration {} ", this);
        if (!slotStart.isBefore(slotEnd)) {
            logger.error("slot ending date {} is after it's start date {}", slotEnd, slotStart);
            return null;
        }

        return new ActionSlot(slotStart, slotEnd, action);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.ActionSlotConfig:: String toString ()
        return super.toString();
    }
}
