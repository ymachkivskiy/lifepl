package pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public class WorldAllowedActionsNumberProperty extends Property<World> {

    @Override
    public Object extract(World propertyHost) {
        return propertyHost.getAllowedActions().size();
    }

    public WorldAllowedActionsNumberProperty() {
        super(StringConstants.WORLD_ALLOWED_ACTIONS_COLUMN, 4);
    }
}
