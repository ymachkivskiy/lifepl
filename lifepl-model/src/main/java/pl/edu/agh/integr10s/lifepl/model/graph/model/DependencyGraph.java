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

    private final DummyFactory<T> edgeFactory = new DummyFactory<>();
    private final DirectedAcyclicGraph<T, Integer> innerRepresentation = new DirectedAcyclicGraph<>(edgeFactory);
    private Set<T> independentElements = new HashSet<>();

    private DependencyGraph() {
        logger.debug("created empty dependency graph");
    }

    DependencyGraph(Set<T> independentElements, List<Dependency<T>> dependencies) {
        logger.debug("... start building dependency graph via builder ...");

        for (T independentElement : independentElements) {
            addElement(independentElement);
        }

        for (Dependency<T> dependency : dependencies) {
            addDependency(dependency);
        }

        logger.debug("... finish building dependency graph via builder ...");
    }

    private boolean addElement(T element) {
        checkNotNull(element);

        logger.debug("add alone element without dependencies : {} ", element);

        if (innerRepresentation.containsVertex(element)) {
            logger.warn("can not add element {} as element without dependencies because it already has dependencies", element);
            return false;
        }

        independentElements.add(element);
        return true;
    }

    private void addDependency(Dependency<T> dependency) {
        for (T predecessor : dependency.getPredecessors()) {
            for (T element : dependency.getElements()) {
                addDependencyBetween(predecessor, element);
            }
        }
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

    @Override
    public <R> DependencyGraph<R> translateSavingDependencies(IdempotentFunction<T, R> translationFunction) {
        checkNotNull(translationFunction);
        logger.debug("translating dependency graph of elements type '{}' to graph with elements of type '{}'", translationFunction.getArgumentType(), translationFunction.getResultType());

        DependencyGraph<R> translatedGraph = new DependencyGraph<>();

        logger.debug("start translating independentElements elements...");
        for (T aloneElement : independentElements) {
            translatedGraph.addElement(translationFunction.calculateFor(aloneElement));
        }
        logger.debug("finish translating independentElements elements");

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

    public int getSize() {
        return innerRepresentation.vertexSet().size() + independentElements.size();
    }

    public Set<T> getElements() {
        return Sets.union(independentElements, innerRepresentation.vertexSet());
    }

    public Iterator<T> iteratorWithDependentOrder() {
        return Iterators.concat(independentElements.iterator(), innerRepresentation.iterator());
    }

    public Set<T> getIndependentElements() {
        return Collections.unmodifiableSet(independentElements);
    }

    public Set<T> getDependentElementsFor(final T element) {
        checkNotNull(element);
        logger.debug("getting elements which depends on {}", element);

        if (!containsElement(element)) {
            logger.warn("try to get dependent elements for element {} , which is not belong to dependency graph", element);
            return Collections.emptySet();
        }

        if (isIndependentElement(element)) {
            logger.debug("element {} has no dependent elements", element);
            return Collections.emptySet();
        }

        return gatherRelatedElements(element, new RelatedElementsExtractor() {
            @Override
            protected Set<Integer> getEdges(T element) {
                return innerRepresentation.outgoingEdgesOf(element);
            }

            @Override
            protected T getEdgeVertex(Integer edge) {
                return innerRepresentation.getEdgeTarget(edge);
            }
        });

    }

    public boolean containsElement(T element) {
        return isIndependentElement(element) || isDependentElement(element);
    }

    public boolean isIndependentElement(T element) {
        return independentElements.contains(element);
    }

    private Set<T> gatherRelatedElements(final T element, final RelatedElementsExtractor elementsExtractor) {
        checkNotNull(element);
        checkNotNull(elementsExtractor);
        logger.debug("gathering related elements for element {}", element);

        Set<T> gatheredElements = new HashSet<>();

        gatheredElements.add(element);

        Stack<T> unprocessedElements = new Stack<>();
        unprocessedElements.push(element);

        while (!unprocessedElements.isEmpty()) {
            T currentElement = unprocessedElements.pop();

            logger.debug("--- currently processed element :: {} ");

            Set<T> relatedElements = elementsExtractor.getRelatedElements(currentElement);

            for (T relatedElement : relatedElements) {
                if (gatheredElements.contains(relatedElement)) {
                    logger.debug("---- got related element {} , which was already processed, skipping it", relatedElement);
                } else {
                    logger.debug("---- got related element {} , which was not processed yet, storing and marking for further processing", relatedElement);
                    gatheredElements.add(relatedElement);
                    unprocessedElements.push(relatedElement);
                }
            }
        }

        gatheredElements.remove(element);

        logger.debug("related elements for element {} were gathered :: ", element, gatheredElements);

        return gatheredElements;
    }

    public boolean isDependentElement(T element) {
        return innerRepresentation.containsVertex(element);
    }

    public Set<T> getElementsOnWhichElementDepends(T element) {
        checkNotNull(element);
        logger.debug("getting elements on which element {} depends on", element);

        if (!containsElement(element)) {
            logger.warn("try to get elements on which element {} depends, which is not belong to dependency graph", element);
            return Collections.emptySet();
        }

        if (isIndependentElement(element)) {
            logger.debug("element {} does not depend on other elements", element);
            return Collections.emptySet();
        }

        return gatherRelatedElements(element, new RelatedElementsExtractor() {
            @Override
            protected Set<Integer> getEdges(T element) {
                return innerRepresentation.incomingEdgesOf(element);
            }

            @Override
            protected T getEdgeVertex(Integer edge) {
                return innerRepresentation.getEdgeSource(edge);
            }
        });
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

    public final static class Dependency<T> {

        private final Set<T> predecessors;
        private final Set<T> elements;

        public Dependency(Set<T> predecessors, Set<T> elements) {
            this.predecessors = Collections.unmodifiableSet(predecessors);
            this.elements = Collections.unmodifiableSet(elements);
        }

        public Set<T> getPredecessors() {
            return predecessors;
        }

        public Set<T> getElements() {
            return elements;
        }
    }

    private abstract class RelatedElementsExtractor {

        public Set<T> getRelatedElements(T element) {
            Set<Integer> edges = getEdges(element);
            Set<T> elements = new HashSet<>();

            for (Integer edge : edges) {
                elements.add(getEdgeVertex(edge));
            }

            return elements;
        }

        protected abstract Set<Integer> getEdges(T element);

        protected abstract T getEdgeVertex(Integer edge);

    }
}
