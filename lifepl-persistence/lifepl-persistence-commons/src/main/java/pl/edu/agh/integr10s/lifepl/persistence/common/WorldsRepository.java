package pl.edu.agh.integr10s.lifepl.persistence.common;

import pl.edu.agh.integr10s.lifepl.model.world.World;

import java.util.Collection;

public interface WorldsRepository {

    public Collection<World> getWorlds();

    public void removeWorld(World world);

    public void addWorld(World world);

    public void updateWorld(World world);
}
