package pl.edu.agh.integr10s.persistence.inmemo;

import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.persistence.common.ActorsRepository;

import java.util.Collection;

public class InMemoryActorsRepository implements ActorsRepository {

    private final InMemoryStorage<Actor> storage = new InMemoryStorage<>(Actor.class);

    @Override
    public Collection<Actor> getActors() {
        return storage.getElements();
    }

    @Override
    public void addActor(Actor actor) {
        storage.addElement(actor);
    }

    @Override
    public void removeActor(Actor actor) {
        storage.removeElement(actor);
    }

    @Override
    public void updateActor(Actor actor) {
        storage.updateElement(actor);
    }
}
