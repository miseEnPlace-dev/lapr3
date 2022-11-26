package isep.model.store;

import isep.model.AgriculturalParcel;

public class AgriculturalParcelStore extends Store<AgriculturalParcel> {
  public AgriculturalParcelStore() {
    super();
  }

  public AgriculturalParcel findParcelByDesignation(String designation) {
    for (AgriculturalParcel a : this.store)
      if (a.getDesignation().equals(designation))
        return a;

    return null;
  }
}
