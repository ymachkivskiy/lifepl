package pl.edu.agh.integr.goal;

import java.util.Iterator;

final class SetActionsGoalDefinition extends GoalDefinition {

  @Override
  public Iterator<Action> iterator() {
    //TODO Yarek : implement
    return null;
  }

  private SetActionsGoalDefinition() {
  }

  private static class Builder extends GoalDefinition.Builder {

    @Override
    public GoalDefinition build() {
      //todo
      return null;
    }
  }

  static Builder Builder() {
    return new Builder();
  }
}
