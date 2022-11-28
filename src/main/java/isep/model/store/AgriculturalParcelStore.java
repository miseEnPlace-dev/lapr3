package isep.model.store;

import java.util.ArrayList;
import java.util.List;
import isep.model.AgriculturalParcel;

public class AgriculturalParcelStore {
  private List<AgriculturalParcel> agriculturalParcels;

  public AgriculturalParcelStore() {
    this.agriculturalParcels = new ArrayList<>();
  }

  public void add(AgriculturalParcel agriculturalParcel) {
    // duplicates
    if (exists(agriculturalParcel))
      throw new IllegalArgumentException("Duplicated agricultural parcel");

    this.agriculturalParcels.add(agriculturalParcel);
  }

  private boolean exists(AgriculturalParcel a) {
    return this.agriculturalParcels.contains(a);
  }

  public AgriculturalParcel findParcelByDesignation(String designation) {
    for (AgriculturalParcel a : this.agriculturalParcels)
      if (a.getDesignation().equals(designation))
        return a;

    return null;
  }

  public int size() {
    return this.agriculturalParcels.size();
  }
}
