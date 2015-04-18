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
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj)
      return true;
    if(obj instanceof Action) {
      Action other = (Action) obj;
      return name.equals(other.name);
    }
    return false;
  }

  @Override
  public String toString() {
    return new StringBuilder()
      .append("action{").append(name).append("}")
      .toString();
  }
}
