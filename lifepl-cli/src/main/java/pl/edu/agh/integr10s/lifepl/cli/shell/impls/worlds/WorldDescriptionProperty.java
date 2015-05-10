package pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public class WorldDescriptionProperty extends Property<World>{

    @Override
    public Object extract(World propertyHost) {
        return propertyHost.getDescription();
    }

    public WorldDescriptionProperty() {
        super(StringConstants.WORLD_DESCRIPTION_COLUMN, 2);
    }
}
