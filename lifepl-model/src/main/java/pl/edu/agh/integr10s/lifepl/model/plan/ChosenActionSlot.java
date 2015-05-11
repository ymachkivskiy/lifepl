package pl.edu.agh.integr10s.lifepl.model.plan;

import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlot;

import java.util.HashSet;
import java.util.Set;

public class ChosenActionSlot {
    private final ActionSlot slot;
    private final Set<Actor> actorsInSlot = new HashSet<>();

    public ActionSlot getSlot() {
        return slot;
    }

    public void removeActorFromSlot(Actor actor) {
        //TODO implement
    }

    public void addActorToSlot(Actor actor) {
        //TODO implement
    }

    public ChosenActionSlot(ActionSlot slot) {
        this.slot = slot;
    }

    @Override
    public int hashCode() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.plan.ChosenActionSlot:: int hashCode ()
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.plan.ChosenActionSlot:: boolean equals ()
        return super.equals(obj);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.plan.ChosenActionSlot:: String toString ()
        return super.toString();
    }
}
