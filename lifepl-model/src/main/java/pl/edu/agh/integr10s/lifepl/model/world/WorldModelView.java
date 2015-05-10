package pl.edu.agh.integr10s.lifepl.model.world;

import java.util.List;
import java.util.Set;

public interface WorldModelView {

    public Set<Action> getAllowedActions();

    public List<ActionSlot> getActionSlots(Action action);
}
