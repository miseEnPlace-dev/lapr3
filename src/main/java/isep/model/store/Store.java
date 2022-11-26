package isep.model.store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Store<T> {
  protected List<T> store;

  public Store() {
    this.store = new ArrayList<>();
  }

  public void add(T t) {
    // duplicates
    if (exists(t))
      throw new IllegalArgumentException("Duplicated element");

    this.store.add(t);
  }

  protected boolean exists(T t) {
    return this.store.contains(t);
  }

  // generic
  // public abstract T find(int field, ...);

  public Iterator<T> getElements() {
    return this.store.iterator();
  }

  public int size() {
    return this.store.size();
  }
}
