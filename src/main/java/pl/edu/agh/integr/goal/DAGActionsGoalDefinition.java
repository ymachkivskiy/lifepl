package pl.edu.agh.integr.goal;

import java.util.Iterator;

final class DAGActionsGoalDefinition extends GoalDefinition {

  private DAGActionsGoalDefinition() {
  }

  private static class Builder extends GoalDefinition.Builder {

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
