package pl.edu.agh.integr.goal;

public enum GoalActionDependency {
  DEPENDENT(
    new BuilderProvider() {
      public GoalDefinition.Builder getBuilder() {
        return GoalDefinition.DependentActionsGoalBuilder();
      }
    }
  ),
  INDEPENDENT(
    new BuilderProvider() {
      public GoalDefinition.Builder getBuilder() {
        return GoalDefinition.IndependentActionsGoalBuilder();
      }
    }
  );

  private final BuilderProvider builderProvider;

  private GoalActionDependency(BuilderProvider builderProvider) {
    this.builderProvider = builderProvider;
  }


  GoalDefinition.Builder getBuilder() {
    return builderProvider.getBuilder();
  }

  private interface BuilderProvider {
    GoalDefinition.Builder getBuilder();
  }
}
