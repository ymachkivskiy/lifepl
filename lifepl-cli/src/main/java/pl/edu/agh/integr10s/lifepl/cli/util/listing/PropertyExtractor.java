package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PropertyExtractor<T> {

    private static final Logger logger = LoggerFactory.getLogger(PropertyExtractor.class);
    private static final Object EMPTY_OBJ = new Object() {
        @Override
        public String toString() {
            return "EMPTY";
        }
    };

    private final Map<String, Property<T>> propertiesMap;
    private final List<String> propertyNames;

    private PropertyExtractor(Set<Property<T>> properties) {
        Map<String, Property<T>> tmpMap = new HashMap<>();
        List<String> tmpNamesList = new ArrayList<>();

        for (Property<T> property : properties) {
            final String propName = property.getName();

            tmpMap.put(propName, property);
            tmpNamesList.add(propName);
        }

        Collections.sort(tmpNamesList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return tmpMap.get(o1).getSortKey() - tmpMap.get(o2).getSortKey();
            }
        });

        propertyNames = Collections.unmodifiableList(tmpNamesList);
        propertiesMap = Collections.unmodifiableMap(tmpMap);
    }

    public static <T> Builder<T> NewBuilder(Class<T> clazz) {
        return new Builder<>();
    }

    public List<String> getPropertyNames() {
        return propertyNames;
    }

    public Object getPropertyValue(int propertyIdx, T hostObject) {
        logger.debug("get property by idx {}", propertyIdx);
        Optional<Property<T>> prop = getProperty(propertyIdx);
        if (prop.isPresent()) {
            return prop.get().extract(hostObject);
        } else {
            logger.warn("property idx {} not found, returning empty object", propertyIdx);
            return EMPTY_OBJ;
        }

    }

    private Optional<Property<T>> getProperty(int propertyIdx) {
        if (propertyIdx < 0 || propertyIdx >= propertyNames.size()) {
            return Optional.empty();
        }
        return getProperty(propertyNames.get(propertyIdx));
    }

    public Object getPropertyValue(String propertyName, T hostObject) {
        logger.debug("get property by name {}", propertyName);
        Optional<Property<T>> prop = getProperty(propertyName);
        if (prop.isPresent()) {
            return prop.get().extract(hostObject);
        } else {
            logger.warn("property {} not found, returning empty object", propertyName);
            return EMPTY_OBJ;
        }
    }

    private Optional<Property<T>> getProperty(String propertyName) {
        return Optional.ofNullable(propertiesMap.get(propertyName));
    }

    public static final class Builder<T> {

        private final Set<Property<T>> props = new HashSet<>();

        private Builder() {
        }

        public PropertyExtractor<T> build() {
            logger.debug("building extractor with propertyNames {} ", props);
            return new PropertyExtractor<>(props);
        }

        public Builder<T> insert(Collection<Property<T>> properties) {
            for (Property<T> property : properties) {
                insert(property);
            }
            return this;
        }

        public Builder<T> insert(Property<T> property) {
            logger.debug("adding property {} ", property);
            this.props.add(property);
            return this;
        }
    }
}
