package pl.edu.agh.integr10s.lifepl.persistance.worlds;

import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.persistance.sample.InMemoryStorage;

import java.util.Collection;

public class InMemoryWorldsService implements WorldsService {

    private final InMemoryStorage<World> storage = new InMemoryStorage<>(World.class);

    @Override
    public Collection<World> getWorlds() {
        return storage.getElements();
    }

    @Override
    public void removeWorld(World world) {
        storage.removeElement(world);
    }

    @Override
    public void addWorld(World world) {
        storage.addElement(world);
    }

    @Override
    public void updateWorld(World world) {
        storage.updateElement(world);
    }
}
