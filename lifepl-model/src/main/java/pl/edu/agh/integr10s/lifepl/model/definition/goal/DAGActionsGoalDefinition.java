package pl.edu.agh.integr10s.lifepl.model.definition.goal;

import com.google.common.collect.Iterators;
import org.jgrapht.EdgeFactory;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

final class DAGActionsGoalDefinition extends GoalDefinition {
    private static final Logger logger = LoggerFactory.getLogger(DAGActionsGoalDefinition.class);
    private DirectedAcyclicGraph<Action, Integer> innerRepresentation = new DirectedAcyclicGraph<Action, Integer>(new Factory());
    private Set<Action> aloneActions = new HashSet<Action>();

    private DAGActionsGoalDefinition() {
    }

    static Builder Builder() {
        return new Builder();
    }

    private void setDependencies(Action source, Action destination) {
        logger.debug("setting dependencies between source={} and desctination={} actions", source, destination);
        try {
            if (!innerRepresentation.containsVertex(source)) {
                logger.debug("goal does not contain action {} yet, adding..", source);
                innerRepresentation.addVertex(source);
            }

            if (!innerRepresentation.containsVertex(destination)) {
                logger.debug("goal does not contain action {} yet, adding..", destination);
                innerRepresentation.addVertex(destination);
            }

            innerRepresentation.addDagEdge(source, destination);
        } catch (DirectedAcyclicGraph.CycleFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAloneAction(Action action) {
        logger.debug("add alone action without dependencies : {} ", action);
        aloneActions.add(action);
    }

    @Override
    public Iterator<Action> iterator() {
        return Iterators.concat(aloneActions.iterator(), innerRepresentation.iterator());
    }

    private static class Factory implements EdgeFactory<Action, Integer> {
        public Integer createEdge(Action action, Action v1) {
            return Integer.MIN_VALUE;
        }
    }

    private static class Builder extends GoalDefinitionBuilder {
        private Set<Action> aloneActions = new HashSet<Action>();
        private List<Dependency> dependencies = new LinkedList<Dependency>();

        @Override
        protected Builder setUpPredecessors(Set<Action> currentActionsPredecessors, Set<Action> currentActions) {
            logger.debug("Setting up predecessors actions {} for actions {}", currentActionsPredecessors, currentActions);
            dependencies.add(new Dependency(currentActionsPredecessors, currentActions));
            return this;
        }

        @Override
        public Builder addAction(Action action) {
            logger.debug("Adding alone action {}", action);
            aloneActions.add(action);
            return this;
        }

        @Override
        public GoalDefinition build() {
            logger.debug("Building new goal definition");

            DAGActionsGoalDefinition resultDefinition = new DAGActionsGoalDefinition();
            for (Action aloneAction : aloneActions) {
                resultDefinition.addAloneAction(aloneAction);
            }
            for (Dependency dependency : dependencies) {
                for (Action pred : dependency.preds) {
                    for (Action action : dependency.actions) {
                        resultDefinition.setDependencies(pred, action);
                    }
                }
            }

            logger.debug("goal definition created");

            return resultDefinition;
        }

        private static class Dependency {
            private final Set<Action> preds;
            private final Set<Action> actions;

            public Dependency(Set<Action> preds, Set<Action> actions) {
                this.preds = preds;
                this.actions = actions;
            }
        }
    }
}
