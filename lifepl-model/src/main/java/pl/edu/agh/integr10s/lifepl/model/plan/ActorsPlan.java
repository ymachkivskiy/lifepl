package pl.edu.agh.integr10s.lifepl.model.plan;

import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.actor.Goal;
import pl.edu.agh.integr10s.lifepl.model.world.Action;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class ActorsPlan {

    private final Actor actor;

    public ActorsPlan(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Collection<ChosenActionSlot> getAllActionSlots() {
        //TODO implement
        return Collections.emptyList();
    }

    public Collection<Goal> getReachedGoals() {
        //TODO implement
        return Collections.emptyList();
    }

    public Collection<Goal> getNotReachedGoals() {
        //TODO implement
        return Collections.emptyList();
    }

    public Collection<ChosenActionSlot> getGoalActionSlots(Goal goal) {
        //TODO implement
        return Collections.emptyList();
    }

    public Optional<ChosenActionSlot> getActionChosenSlot(Action action) {
        //TODO implement
        return null;
    }


    @Override
    public int hashCode() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.plan.ActorsPlan:: int hashCode ()
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.plan.ActorsPlan:: boolean equals ()
        return super.equals(obj);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.plan.ActorsPlan:: String toString ()
        return super.toString();
    }
}
