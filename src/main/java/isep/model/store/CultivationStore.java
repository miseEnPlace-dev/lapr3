package isep.model.store;

import isep.model.Cultivation;

public class CultivationStore extends Store<Cultivation> {
  public CultivationStore() {
    super();
  }

  public Cultivation findCultivationByDesignation(String designation) {
    for (Cultivation a : store)
      if (a.getDesignation().equals(designation))
        return a;

    return null;
  }
}
