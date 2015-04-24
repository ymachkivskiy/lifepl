package pl.edu.agh.integr10s.lifepl.model.goal;

public enum GoalActionDependency {
  DEPENDENT(
    new BuilderProvider() {
      public GoalDefinitionBuilder getBuilder() {
        return GoalDefinition.DependentActionsGoalBuilder();
      }
    }
  ),
  INDEPENDENT(
    new BuilderProvider() {
      public GoalDefinitionBuilder getBuilder() {
        return GoalDefinition.IndependentActionsGoalBuilder();
      }
    }
  );

  private final BuilderProvider builderProvider;

  private GoalActionDependency(BuilderProvider builderProvider) {
    this.builderProvider = builderProvider;
  }


  GoalDefinitionBuilder getBuilder() {
    return builderProvider.getBuilder();
  }

  private interface BuilderProvider {
    GoalDefinitionBuilder getBuilder();
  }
}
