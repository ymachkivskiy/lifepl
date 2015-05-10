package pl.edu.agh.integr10s.lifepl.persistance.actors;

import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.persistance.sample.InMemoryStorage;

import java.util.Collection;

public class InMemoryActorsService implements ActorsService {

    private final InMemoryStorage<Actor> storage = new InMemoryStorage<>(Actor.class);

    {//TODO remove it
        for (Actor actor : Actor.sampleActors()) {
            storage.addElement(actor);
        }
    }

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
