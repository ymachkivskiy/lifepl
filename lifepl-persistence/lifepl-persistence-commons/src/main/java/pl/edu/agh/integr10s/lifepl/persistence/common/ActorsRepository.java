package pl.edu.agh.integr10s.lifepl.persistence.common;


import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

import java.util.Collection;

public interface ActorsRepository {

    public Collection<Actor> getActors();

    public void addActor(Actor actor);

    public void removeActor(Actor actor);

    public void updateActor(Actor actor);
}
