package pl.edu.agh.integr.goal;

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
    return super.hashCode(); //TODO Yarek : implement
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj); //TODO Yarek : implement
  }

  @Override
  public String toString() {
    return super.toString(); //TODO Yarek : implement
  }
}
