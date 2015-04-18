package pl.edu.agh.integr.goal;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class TaskStatus {
  private final Action action;
  private boolean isDone;

  public TaskStatus(Action action) {
    this.action = action;
    this.isDone = false;
  }

  public Action getAction() {
    return action;
  }

  public boolean isDone() {
    return isDone;
  }

  public void setIsDone(boolean isDone) {
    this.isDone = isDone;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31)
        .append(action.hashCode())
        .append(isDone)
      .hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj instanceof TaskStatus) {
      TaskStatus other = (TaskStatus) obj;
      return isDone == other.isDone && action.equals(other.action);
    }
    return false;
  }

  @Override
  public String toString() {
    return new StringBuilder()
      .append("task{").append(action).append("-").append(isDone ? "DONE" : "IN_PROCESS").append("}")
      .toString();
  }
}
