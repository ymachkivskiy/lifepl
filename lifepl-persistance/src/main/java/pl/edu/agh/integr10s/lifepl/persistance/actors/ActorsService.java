package pl.edu.agh.integr10s.lifepl.persistance.actors;

import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

import java.util.Collection;

public interface ActorsService {

    public Collection<Actor> getActors();

    public void addActor(Actor actor);

    public void removeActor(Actor actor);
}
