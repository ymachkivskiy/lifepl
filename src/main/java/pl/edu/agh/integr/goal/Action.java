package pl.edu.agh.integr.goal;

public abstract class Action {
  private final String name;

  public String getName() {
    return name;
  }

  public Action(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    //TODO Yarek : implement
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    //TODO Yarek : implement
    return super.equals(obj);
  }

  @Override
  public String toString() {
    //TODO Yarek : implement
    return super.toString();
  }
}
