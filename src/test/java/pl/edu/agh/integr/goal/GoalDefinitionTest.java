package pl.edu.agh.integr.goal;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.fail;

public abstract class GoalDefinitionTest {

  protected GoalDefinition goalDefinition;
  protected Set<Action> containingActions = new HashSet<Action>();

  @Test
  public void testContainsAllActions(){
    fail("NOT IMPLEMENTED");
  }
}
