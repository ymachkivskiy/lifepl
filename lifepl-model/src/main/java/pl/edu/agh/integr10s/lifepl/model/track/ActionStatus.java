package pl.edu.agh.integr10s.lifepl.model.track;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.edu.agh.integr10s.lifepl.model.world.Action;

public final class ActionStatus {
    private final Action action;
    private boolean isDone;

    public ActionStatus(Action action) {
        this.action = action;
        this.isDone = false;
    }

    public Action getAction() {
        return action;
    }

    public boolean isDone() {
        return isDone;
    }

    void markDone() {
        isDone = true;
    }

    void markUnDone() {
        isDone = false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(action.hashCode())
                .append(isDone)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof ActionStatus) {
            ActionStatus other = (ActionStatus) obj;
            return isDone == other.isDone && action.equals(other.action);
        }
        return false;
    }

    @Override
    public String toString() {
        return "task{" + action + "::" + (isDone ? "DONE" : "NOT_DONE") + "}";
    }
}
