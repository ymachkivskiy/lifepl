package pl.edu.agh.integr10s.lifepl.model.definition.goal;

import org.apache.log4j.Logger;

import java.util.*;

final class SetActionsGoalDefinition extends GoalDefinition {
  private final static Logger logger = Logger.getLogger(SetActionsGoalDefinition.class);

  private final Collection<Action> actions;

  @Override
  public Iterator<Action> iterator() {
    return actions.iterator();
  }

  private SetActionsGoalDefinition(Collection<Action> actions) {
    logger.debug("Constructed goal definition with actions : " + actions);
    this.actions = Collections.unmodifiableCollection(actions);
  }

  private static class Builder extends GoalDefinitionBuilder {
    private final Set<Action> actions = new HashSet<Action>();

    @Override
    protected Builder setUpPredecessors(Set<Action> currentActionsPredecessors, Set<Action> currentActions) {
      logger.error("invoking unsuported operation for goal definition builder : setUpPredecessors with currentActionsPredecessors="
        + currentActionsPredecessors + " and currentActions=" + currentActions);
      throw new UnsupportedOperationException();
    }

    @Override
    public Builder addAction(Action action) {
      logger.debug("adding action " + action + " to builder");
      actions.add(action);
      return this;
    }

    @Override
    public PredecessorActionList withPredecessors() {
      logger.error("invoking unsuported operation for goal definition builder : withPredecessors");
      throw new UnsupportedOperationException();
    }

    @Override
    public GoalDefinition build() {
      logger.debug("building new goal definition");
      return new SetActionsGoalDefinition(new LinkedHashSet<Action>(actions));
    }
  }

  static Builder Builder() {
    return new Builder();
  }
}
