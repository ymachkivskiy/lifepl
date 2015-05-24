package pl.edu.agh.integr10s.lifepl.cli.props;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.PropertyExtractor;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.world.Action;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlot;
import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;

public final class ActionSlotProperties {
    private ActionSlotProperties() {
    }

    public static final Property<ActionSlot> SLOT_BEGIN_TIME = new SlotBeginTimeProperty(2);
    public static final Property<ActionSlot> SLOT_END_TIME = new SlotEndTimeProperty(3);
    public static final Property<ActionSlot> SLOT_RESTRICTIONS = new SlotRestrictionsProperty(4);

    public static final PropertyExtractor<ActionSlot> PROPERTY_EXTRACTOR = PropertyExtractor.NewBuilder(ActionSlot.class)
            .insert(SLOT_BEGIN_TIME)
            .insert(SLOT_END_TIME)
            .insert(SLOT_RESTRICTIONS)
            .build();


    public static PropertyExtractor<Action> SlotsNumberForActionExtractor(World world) {
        return PropertyExtractor.NewBuilder(Action.class)
                .insert(ActionProperties.ACTION_NAME)
                .insert(new SlotsCountActionProperty(2, world))
                .build();
    }

    private static class SlotsCountActionProperty extends Property<Action> {
        private final World world;

        public SlotsCountActionProperty(int sortKey, World world) {
            super(StringConstants.ACTION_SLOTS_NUMBER_COLUMN, sortKey);
            this.world = world;
        }

        @Override
        public Object extract(Action propertyHost) {
            return world.getActionSlots(propertyHost).size();
        }

    }

    private static class SlotBeginTimeProperty extends  Property<ActionSlot> {
        public SlotBeginTimeProperty(int sortKey) {
            super(StringConstants.ACTION_SLOT_BEGIN_TIME_COLUMN, sortKey);
        }

        @Override
        public Object extract(ActionSlot propertyHost) {
            return propertyHost.getBeginOfSlot();
        }
    }

    private static class SlotEndTimeProperty extends  Property<ActionSlot> {
        public SlotEndTimeProperty(int sortKey) {
            super(StringConstants.ACTION_SLOT_END_TIME_COLUMN, sortKey);
        }

        @Override
        public Object extract(ActionSlot propertyHost) {
            return propertyHost.getEndOfSlot();
        }
    }

    private static class SlotRestrictionsProperty extends Property<ActionSlot> {

        @Override
        public Object extract(ActionSlot propertyHost) {
            StringBuilder sb = new StringBuilder();
            for (SlotRestriction slotRestriction : propertyHost.getRestrictions()) {
                sb.append("[").append(slotRestriction).append("];  ");
            }
            return sb.toString();
        }

        public SlotRestrictionsProperty(int sortKey) {
            super(StringConstants.ACTION_SLOT_RESTRICTIONS_COLUMN, sortKey);
        }
    }
}
