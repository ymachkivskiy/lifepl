package pl.edu.agh.integr10s.lifepl.persistance.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryActorsService implements ActorsService {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryActorsService.class);

    private Set<Actor> storedActors = new HashSet<>();

    public InMemoryActorsService() {
        logger.debug("initializing \"in memory\" actors service", this);
        //TODO remove it
        addActor(new Actor("Wiktor"));
        addActor(new Actor("Alex"));
        addActor(new Actor("Zbyszek"));
    }

    @Override
    public Collection<Actor> getActors() {
        return Collections.unmodifiableSet(storedActors);
    }

    @Override
    public void addActor(Actor actor) {
        logger.info("add actor {} to list of actors", actor);
        if(!storedActors.add(actor)) {
            logger.warn("actor {} was already in actors list", actor);
        }
    }

    @Override
    public void removeActor(Actor actor) {
        logger.info("remove actor {} from list of actors", actor);
        if (!storedActors.remove(actor)) {
            logger.warn("actor {} was not found in actors list", actor);
        }
    }
}
