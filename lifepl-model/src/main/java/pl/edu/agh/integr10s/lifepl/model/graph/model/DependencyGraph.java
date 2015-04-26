package pl.edu.agh.integr10s.lifepl.model.graph.model;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import org.jgrapht.EdgeFactory;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;

public class DependencyGraph<T> implements Translatable<T> {

    private static final Logger logger = LoggerFactory.getLogger(DependencyGraph.class);

    private final DummyFactory<T> edgeFactory = new DummyFactory<T>();
    private DirectedAcyclicGraph<T, Integer> innerRepresentation = new DirectedAcyclicGraph<T, Integer>(edgeFactory);
    private Set<T> aloneElements = new HashSet<T>();

    @Override
    public <R> Translatable<R> translateSavingDependencies(IdempotentFunction<T, R> translationFunction) {
        checkNotNull(translationFunction);
        logger.debug("translating dependency graph of elements type '{}' to graph with elements of type '{}'", translationFunction.getArgumentType(), translationFunction.getResultType());

        DependencyGraph<R> translatedGraph = new DependencyGraph<>();

        logger.debug("start translating independent elements...");
        for (T aloneElement : aloneElements) {
            translatedGraph.addElement(translationFunction.calculateFor(aloneElement));
        }
        logger.debug("finish translating independent elements");

        logger.debug("start translating elements with dependencies...");
        for (Integer edgeIdx : innerRepresentation.edgeSet()) {
            Optional<Edge<T>> edge = edgeFactory.getEdge(edgeIdx);
            if (edge.isPresent()) {
                R element = translationFunction.calculateFor(edge.get().getElement());
                R dependentElement = translationFunction.calculateFor(edge.get().getDependantElement());
                translatedGraph.addDependencyBetween(element, dependentElement);
            } else {
                logger.error("edge with index {} is not present, but it should be", edgeIdx);
            }
        }
        logger.debug("finish translating elements with dependencies");

        return translatedGraph;
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

    private static class Edge<T> {

        private final T element;
        private final T dependantElement;

        public Edge(T element, T dependantElement) {
            this.element = element;
            this.dependantElement = dependantElement;
        }

        public T getElement() {
            return element;
        }

        public T getDependantElement() {
            return dependantElement;
        }
    }

    private static class DummyFactory<T> implements EdgeFactory<T, Integer> {

        private final AtomicInteger counter = new AtomicInteger(0);
        private final Map<Integer, Edge<T>> edgeMap = new HashMap<>();

        public Integer createEdge(T element, T dependantElement) {
            final int idx = counter.getAndIncrement();
            edgeMap.put(idx, new Edge<T>(element, dependantElement));
            return idx;
        }

        public Optional<Edge<T>> getEdge(Integer e) {
            return Optional.ofNullable(edgeMap.get(e));
        }
    }
}
