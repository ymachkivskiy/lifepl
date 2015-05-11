package pl.edu.agh.integr10s.engine.resolve;

import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.Action;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlot;

import java.util.List;

public interface ProblemContext {

    public List<Actor> getActors();

    public List<ActionSlot> getActionSlots(Action action);
}
