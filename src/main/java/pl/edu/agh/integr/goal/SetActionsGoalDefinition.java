package pl.edu.agh.integr.goal;

import java.util.*;

final class SetActionsGoalDefinition extends GoalDefinition {
  private final Collection<Action> actions;

  @Override
  public Iterator<Action> iterator() {
    return actions.iterator();
  }

  private SetActionsGoalDefinition(Collection<Action> actions) {
    this.actions = Collections.unmodifiableCollection(actions);
  }

  private static class Builder extends GoalDefinitionBuilder {
    private final Set<Action> actions = new HashSet<Action>();

    @Override
    protected Builder setUpPredecessors(Set<Action> currentActionsPredecessors, Set<Action> currentActions) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Builder addAction(Action action) {
      actions.add(action);
      return this;
    }

    @Override
    public PredecessorActionList withPredecessors() {
      throw new UnsupportedOperationException();
    }

    @Override
    public GoalDefinition build() {
      return new SetActionsGoalDefinition(new LinkedHashSet<Action>(actions));
    }
  }

  static Builder Builder() {
    return new Builder();
  }
}
