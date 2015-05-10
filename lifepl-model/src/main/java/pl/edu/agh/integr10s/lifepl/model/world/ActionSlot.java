package pl.edu.agh.integr10s.lifepl.model.world;

import java.time.LocalDateTime;

public final class ActionSlot {
    private final LocalDateTime beginOfSlot;
    private final LocalDateTime endOfSlot;
    private final Action action;

    //  slot cost properties (can be energy)
    // slot restrictions (amount of people , for example)

    ActionSlot(LocalDateTime beginOfSlot, LocalDateTime endOfSlot, Action action) {
        this.beginOfSlot = beginOfSlot;
        this.endOfSlot = endOfSlot;
        this.action = action;
    }

    public LocalDateTime getBeginOfSlot() {
        return beginOfSlot;
    }

    public LocalDateTime getEndOfSlot() {
        return endOfSlot;
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
