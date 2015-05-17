package pl.edu.agh.integr10s.lifepl.cli.props;

import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.PropertyExtractor;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;

public final class PlaningProperties {
    private PlaningProperties() {
    }


    public static final Property<Planning> PLANING_ENGINE_CLASS = new PlaningEngineClassProperty(1);
    public static final Property<Planning> PLANING_CONTEXT = new PlaningContextProperty(2);
    public static final Property<Planning> PLANING_RESULT = new PlaningResultProperty(3);
    public static final Property<Planning> PLANING_BEGIN_TIME = new PlaningBeginTimeProperty(4);
    public static final Property<Planning> PLANING_END_TIME = new PlaningEndTimeProperty(5);

    public static final PropertyExtractor<Planning> PROPERTY_EXTRACTOR = PropertyExtractor.NewBuilder(Planning.class)
        .insert(PLANING_BEGIN_TIME)
        .insert(PLANING_CONTEXT)
        .insert(PLANING_ENGINE_CLASS)
        .insert(PLANING_END_TIME)
        .insert(PLANING_RESULT)
        .build();

    private static class PlaningResultProperty extends Property<Planning> {

        @Override
        public Object extract(Planning propertyHost) {
            return null; //TODO
        }

        public PlaningResultProperty(int sortKey) {
            super(StringConstants.PLANING_RESULT_COLUMN, sortKey);
        }
    }

    private static class PlaningBeginTimeProperty extends Property<Planning> {
        @Override
        public Object extract(Planning propertyHost) {
            return propertyHost.getPlaningBeginTime();
        }

        public PlaningBeginTimeProperty(int sortKey) {
            super(StringConstants.PLANING_BEGIN_TIME_COLUMN, sortKey);
        }
    }

    private static class PlaningEndTimeProperty extends Property<Planning> {
        @Override
        public Object extract(Planning propertyHost) {
            return propertyHost.getPlaningEndTime();
        }

        public PlaningEndTimeProperty(int sortKey) {
            super(StringConstants.PLANING_END_TIME_COLUMN, sortKey);
        }
    }

    private static class PlaningContextProperty extends Property<Planning> {
        @Override
        public Object extract(Planning propertyHost) {
            return null; //TODO
        }

        public PlaningContextProperty(int sortKey) {
            super(StringConstants.PLANING_PROBLEM_CONTEXT_COLUMN, sortKey);
        }
    }

    private static class PlaningEngineClassProperty extends Property<Planning> {
        @Override
        public Object extract(Planning propertyHost) {
            return propertyHost.getExecutiveEngineClass().getEngineDescription().getName();
        }

        public PlaningEngineClassProperty(int sortKey) {
            super(StringConstants.PLANING_ENGINE_CLASS_COLUMN, sortKey);
        }
    }
}
