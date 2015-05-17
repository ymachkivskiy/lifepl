package pl.edu.agh.integr10s.lifepl.cli.props;

import pl.edu.agh.integr10s.engine.factory.EngineFactory;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.PropertyExtractor;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;

public final class EngineProperties {
    private EngineProperties() {
    }

    public static final Property<EngineFactory> NAME = new EngineNameProperty(1);
    public static final Property<EngineFactory> DESCRIPTION = new EngineDescriptionProperty(2);
    public static final Property<EngineFactory> ALGORITHM_DESCRIPTION = new EngineAlgorithmProperty(3);
    public static final Property<EngineFactory> ALGORITHM_COMPLEXITY = new EngineAlgorithmComplexityProperty(4);
    public static final Property<EngineFactory> ALGORITHM_EFFICIENCY = new EngineAlgorithmEfficiencyProperty(5);


    public static final PropertyExtractor<EngineFactory> PROPERTY_EXTRACTOR = PropertyExtractor.NewBuilder(EngineFactory.class)
        .insert(NAME)
        .insert(DESCRIPTION)
        .insert(ALGORITHM_COMPLEXITY)
        .insert(ALGORITHM_DESCRIPTION)
        .insert(ALGORITHM_EFFICIENCY)
        .build();

    private static class EngineNameProperty extends Property<EngineFactory> {

        @Override
        public Object extract(EngineFactory propertyHost) {
            return propertyHost.getEngineDescription().getName();
        }

        public EngineNameProperty(int sortKey) {
            super(StringConstants.ENGINE_NAME_COLUMN, sortKey);
        }
    }

    private static class EngineDescriptionProperty extends Property<EngineFactory> {

        @Override
        public Object extract(EngineFactory propertyHost) {
            return propertyHost.getEngineDescription().getDescription();
        }

        public EngineDescriptionProperty(int sortKey) {
            super(StringConstants.ENGINE_DESCRIPTION_COLUMN, sortKey);
        }
    }

    private static class EngineAlgorithmProperty extends Property<EngineFactory> {
        @Override
        public Object extract(EngineFactory propertyHost) {
            return propertyHost.getEngineDescription().getAlgorithm();
        }

        public EngineAlgorithmProperty(int sortKey) {
            super(StringConstants.ENGINE_ALGORITHM_COLUMN, sortKey);
        }
    }

    private static class EngineAlgorithmComplexityProperty extends Property<EngineFactory> {
        @Override
        public Object extract(EngineFactory propertyHost) {
            return propertyHost.getEngineDescription().getAlgorithmComplexity();
        }

        public EngineAlgorithmComplexityProperty(int sortKey) {
            super(StringConstants.ENGINE_ALGORITHM_COMPLEXITY_COLUMN, sortKey);
        }
    }

    private static class EngineAlgorithmEfficiencyProperty extends Property<EngineFactory> {
        @Override
        public Object extract(EngineFactory propertyHost) {
            return propertyHost.getEngineDescription().getAlgorithmEfficiency();
        }

        public EngineAlgorithmEfficiencyProperty(int sortKey) {
            super(StringConstants.ENGINE_EFFICIENCY_COLUMN, sortKey);
        }
    }
}
