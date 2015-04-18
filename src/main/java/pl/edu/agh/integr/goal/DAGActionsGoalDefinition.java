package pl.edu.agh.integr.goal;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

final class DAGActionsGoalDefinition extends GoalDefinition {

  private DAGActionsGoalDefinition() {
  }

  private static class Builder extends GoalDefinitionBuilder {
    @Override
    protected Builder setUpPredecessors(Set<Action> currentActionsPredecessors, Set<Action> currentActions) {
      //TODO
      return this;
    }

    @Override
    public Builder addAction(Action action) {
      //TODO
      return this;
    }

    @Override
    public GoalDefinition build() {
      //TODO Yarek : implement
      return new DAGActionsGoalDefinition();
    }
  }

  @Override
  public Iterator<Action> iterator() {
    return Collections.emptyIterator();
  }

  static Builder Builder() {
    return new Builder();
  }
}
