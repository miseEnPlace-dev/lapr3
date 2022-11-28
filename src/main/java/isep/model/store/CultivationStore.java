package isep.model.store;

import java.util.ArrayList;
import java.util.List;
import isep.model.Cultivation;

public class CultivationStore {
  private List<Cultivation> cultivations;

  public CultivationStore() {
    this.cultivations = new ArrayList<>();
  }

  public void add(Cultivation cultivation) {
    // duplicates
    if (exists(cultivation))
      throw new IllegalArgumentException("Duplicated agricultural parcel");

    this.cultivations.add(cultivation);
  }

  private boolean exists(Cultivation a) {
    return this.cultivations.contains(a);
  }

  public Cultivation findCultivationByDesignation(String designation) {
    for (Cultivation a : this.cultivations)
      if (a.getDesignation().equals(designation))
        return a;

    return null;
  }

  public int size() {
    return this.cultivations.size();
  }
}
