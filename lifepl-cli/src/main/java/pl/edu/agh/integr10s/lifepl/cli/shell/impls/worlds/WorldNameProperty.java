package pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public class WorldNameProperty extends Property<World> {

    public WorldNameProperty() {
        super(StringConstants.WORLD_NAME_COLUMN, 1);
    }

    @Override
    public Object extract(World propertyHost) {
        return propertyHost.getName();
    }
}
