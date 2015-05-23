package pl.edu.agh.integr10s.persistance.inmemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryStorage<T> {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryStorage.class);

    private final Class<T> elementsType;
    private Set<T> elements = new HashSet<>();

    public InMemoryStorage(Class<T> elementsType) {
        logger.debug("initializing \"in memory\"  service for elements of type {} ", elementsType);
        this.elementsType = elementsType;
    }

    public Collection<T> getElements() {
        logger.debug("get [{}] elements of type {} from storage", elements.size(), elementsType);
        return Collections.unmodifiableSet(elements);
    }

    public void removeElement(T element) {
        logger.debug("remove element {} of type {} from storage with [{}] elements", element, elementsType, elements.size());
        elements.remove(element);
        logger.debug("after remove operation storage contains [{}] elements", elements.size());
    }

    public void addElement(T element) {
        logger.debug("add element {} of type {} to storage with [{}] elements", element, elementsType, elements.size());
        elements.add(element);
        logger.debug("after add operation storage contains [{}] elements", elements.size());
    }

    public void updateElement(T element) {
        logger.debug("update element {} of type {} inside storage with [{}] elements", element, elementsType, elements.size());
        if (elements.add(element)) {
            logger.debug("element {} was not in storage before this moment", element);
        }
        logger.debug("after update operation storage contains [{}] elements", elements.size());
    }
}
