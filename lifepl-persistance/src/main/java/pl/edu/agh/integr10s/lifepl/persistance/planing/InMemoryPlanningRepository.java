package pl.edu.agh.integr10s.lifepl.persistance.planing;

import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.lifepl.persistance.common.PlaningRepository;
import pl.edu.agh.integr10s.lifepl.persistance.sample.InMemoryStorage;

import java.util.Collection;

public class InMemoryPlanningRepository implements PlaningRepository {

    private final InMemoryStorage<Planning> storage = new InMemoryStorage<>(Planning.class);

    @Override
    public Collection<Planning> getAllPlannings() {
        return storage.getElements();
    }

    @Override
    public void addPlaning(Planning planning) {
        storage.addElement(planning);
    }
}
