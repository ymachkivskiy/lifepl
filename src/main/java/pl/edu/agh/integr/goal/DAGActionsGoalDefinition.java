package pl.edu.agh.integr.goal;

import java.util.Iterator;
import java.util.Set;

final class DAGActionsGoalDefinition extends GoalDefinition {

  private DAGActionsGoalDefinition() {
  }

  private static class Builder extends GoalDefinitionBuilder {
    @Override
    protected Builder setUpPredecessors(Set<Action> currentActionsPredecessors, Set<Action> currentActions) {
      //TODO
      return null;
    }

    @Override
    public Builder addAction(Action action) {
      //TODO
      return null;
    }

    @Override
    public GoalDefinition build() {
      //TODO Yarek : implement
      return null;
    }
  }

  @Override
  public Iterator<Action> iterator() {
    //TODO Yarek : implement
    return null;
  }

  static Builder Builder() {
    return new Builder();
  }
}
