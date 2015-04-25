package pl.edu.agh.integr10s.lifepl.model.graph.model;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import org.jgrapht.EdgeFactory;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class DependencyGraph<T> {

    private static final Logger logger = LoggerFactory.getLogger(DependencyGraph.class);

    private DirectedAcyclicGraph<T, Integer> innerRepresentation = new DirectedAcyclicGraph<T, Integer>(new DummyFactory());
    private Set<T> aloneElements = new HashSet<T>();

    private class DummyFactory implements EdgeFactory<T, Integer> {

        public Integer createEdge(T action, T v1) {
            return Integer.MIN_VALUE;
        }
    }

    public <R> DependencyGraph<R> translate(Function<T, R> translationFunction) {
        checkNotNull(translationFunction);
        logger.debug("translating dependency graph");

        DependencyGraph<R> translatedGraph = new DependencyGraph<>();

        for (T aloneElement : aloneElements) {
            translatedGraph.addElement(translationFunction.apply(aloneElement));
        }

//        innerRepresentation.

        return null;//TODO implement
    }

    private void addDependencyBetween(T element, T dependantElement) {
        checkNotNull(element);
        checkNotNull(dependantElement);

        logger.debug("setting dependency : {} depends on {}", dependantElement, element);
        try {
            if (!innerRepresentation.containsVertex(element)) {
                logger.debug("goal does not contain {} yet, adding...", element);
                innerRepresentation.addVertex(element);
            }

            if (!innerRepresentation.containsVertex(dependantElement)) {
                logger.debug("goal does not contain {} yet, adding...", dependantElement);
                innerRepresentation.addVertex(dependantElement);
            }

            if (innerRepresentation.containsEdge(element, dependantElement)) {
                logger.debug("dependency between {} and {} already exist", element, dependantElement);
            } else {
                innerRepresentation.addDagEdge(element, dependantElement);
            }

        } catch (DirectedAcyclicGraph.CycleFoundException e) {
            logger.warn("cycle found after adding dependency between {} and {}", element, dependantElement);
            throw new RuntimeException(e);
        }
    }

    private boolean addElement(T element) {
        checkNotNull(element);

        logger.debug("add alone element without dependencies : {} ", element);

        if (innerRepresentation.containsVertex(element)) {
            logger.warn("can not add element {} as element without dependencies because it already has dependencies", element);
            return false;
        }

        aloneElements.add(element);
        return true;
    }

    public Set<T> getElements() {
        return Sets.union(aloneElements, innerRepresentation.vertexSet());
    }

    public Iterator<T> iteratorWithDependentOrder() {
        return Iterators.concat(aloneElements.iterator(), innerRepresentation.iterator());
    }

    public Set<T> getIndependentElements() {
        return Collections.unmodifiableSet(aloneElements);
    }

    public Set<T> getDependentElementsFor(T element) {
        checkNotNull(element);
        return Collections.emptySet(); // TODO implement
    }

    public Set<T> getElementsOnWhichElementDepends(T element) {
        checkNotNull(element);
        return Collections.emptySet(); // TODO implement
    }
}
