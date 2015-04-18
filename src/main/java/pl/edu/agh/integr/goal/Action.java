package pl.edu.agh.integr.goal;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    return new HashCodeBuilder(17, 137)
      .append(name)
      .hashCode();
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
