package pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public class WorldActorsNumberProperty extends Property<World> {

    @Override
    public Object extract(World propertyHost) {
        return propertyHost.getActors().size();
    }

    public WorldActorsNumberProperty() {
        super(StringConstants.WORLD_ACTORS_NUMBER_COLUMN, 3);
    }
}
