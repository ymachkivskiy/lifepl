package pl.edu.agh.integr10s.lifepl.model.graph.model;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DependencyGraphBuilder<T> {

    private static final Logger logger = LoggerFactory.getLogger(DependencyGraphBuilder.class);

    private Set<T> independentElements = new HashSet<>();
    private List<Dependency<T>> dependencies = new LinkedList<>();

    public DependencyGraph<T> build() {
        logger.debug("Building new dependency graph");

        DependencyGraph<T> resultDefinition = new DependencyGraph<>();
        for (T aloneAction : independentElements) {
            resultDefinition.addElement(aloneAction);
        }

        for (Dependency<T> dependency : dependencies) {
            for (T predecessor : dependency.predecessors) {
                for (T element : dependency.elements) {
                    resultDefinition.addDependencyBetween(predecessor, element);
                }
            }
        }

        logger.debug("dependency graph created");

        return resultDefinition;
    }

    protected DependencyGraphBuilder<T> setUpPredecessors(Set<T> currentElementsPredecessors, Set<T> currentElements) {
        dependencies.add(new Dependency<T>(currentElementsPredecessors, currentElements));
        return this;
    }

    public DependencyGraphBuilder<T> addElement(T element) {
        independentElements.add(element);
        return this;
    }

    public PredecessorElementsList withPredecessors() {
        logger.info("... start to construct list of predecessor elements ...");
        return new PredecessorElementsList(this);
    }

    public PredecessorsElementsListToElementsListBridge<T> withPredecessor(T element) {
        logger.info("constructed list of predecessor elements with one element : {} ", element);
        return new PredecessorsElementsListToElementsListBridge(this, new PredecessorElementsList(this).addElement(element));
    }

    public static class PredecessorsElementsListToElementsListBridge<T> {

        private final DependencyGraphBuilder<T> builder;
        private final PredecessorElementsList<T> predecessorElementsList;

        public PredecessorsElementsListToElementsListBridge(DependencyGraphBuilder<T> builder, PredecessorElementsList<T> predecessorElementsList) {
            this.builder = builder;
            this.predecessorElementsList = predecessorElementsList;
        }

        public ElementsList<T> forElements() {
            logger.info("... start to construct list of elements ...");
            return new ElementsList<T>(builder, predecessorElementsList);
        }

        public DependencyGraphBuilder<T> forElement(T element) {
            logger.info("constructed list of elements with one element : {}", element);
            return builder.setUpPredecessors(predecessorElementsList.getPredecessors(), Sets.newHashSet(element));
        }
    }

    public static class PredecessorElementsList<T> {

        private final DependencyGraphBuilder<T> builder;
        private Set<T> predecessors = new HashSet<T>();

        private PredecessorElementsList(DependencyGraphBuilder<T> builder) {
            this.builder = builder;
        }

        public PredecessorElementsList<T> addElement(T element) {
            logger.info("add element to predecessors elements list : {}", element);
            predecessors.add(element);
            return this;
        }

        public ElementsList<T> forElements() {
            logger.info("... finish to construct predecessors elements list ...");
            logger.info("... start to construct list of elements ...");
            return new ElementsList<T>(builder, this);
        }

        public DependencyGraphBuilder<T> forElement(T element) {
            logger.info("constructed elements list of single element : {}", element);
            return builder.setUpPredecessors(predecessors, Sets.newHashSet(element));
        }

        Set<T> getPredecessors() {
            return Collections.unmodifiableSet(predecessors);
        }
    }

    public static class ElementsList<T> {

        private final Set<T> predecessors;
        private final Set<T> elements = new HashSet<>();
        private final DependencyGraphBuilder<T> builder;

        private ElementsList(DependencyGraphBuilder<T> builder, PredecessorElementsList<T> predecessorActionList) {
            this.builder = builder;
            this.predecessors = predecessorActionList.getPredecessors();
        }

        public ElementsList<T> addElement(T element) {
            logger.info("add element to elements list : {}", element);
            elements.add(element);
            return this;
        }

        public DependencyGraphBuilder<T> accept() {
            logger.info("finish to construct list of elements");
            return builder.setUpPredecessors(predecessors, elements);
        }

    }

    private static class Dependency<T> {

        private final Set<T> predecessors;
        private final Set<T> elements;

        public Dependency(Set<T> predecessors, Set<T> elements) {
            this.predecessors = predecessors;
            this.elements = elements;
        }
    }

}
