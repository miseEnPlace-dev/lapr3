package isep.mock;

import java.util.ArrayList;
import java.util.List;
import isep.controller.App;
import isep.model.AgriculturalParcel;
import isep.model.Cultivation;
import isep.model.store.AgriculturalParcelStore;
import isep.model.store.CultivationStore;
import isep.shared.Hour;
import isep.shared.exceptions.InvalidHourFormatException;

public class ParcelsMock {
  public static void mockParcels() throws InvalidHourFormatException {
    List<Hour> hours = new ArrayList<>();
    hours.add(new Hour(8, 30));
    hours.add(new Hour(15, 0));

    CultivationStore cStore = App.getInstance().getCompany().getCultivationStore();
    AgriculturalParcelStore pStore = App.getInstance().getCompany().getAgriculturalParcelStore();

    Cultivation c = new Cultivation("C1", false);
    if (cStore.findCultivationByDesignation("C1") == null)
      cStore.add(c);
    AgriculturalParcel p1 = new AgriculturalParcel("Parcela1", 10, c);
    AgriculturalParcel p2 = new AgriculturalParcel("Parcela2", 10, c);
    AgriculturalParcel p3 = new AgriculturalParcel("Parcela3", 10, c);

    if (pStore.findParcelByDesignation("Parcela1") == null) {
      pStore.add(p1);
      pStore.add(p2);
      pStore.add(p3);
    }
  }
}
