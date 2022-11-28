package isep.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import isep.controller.App;
import isep.model.AgriculturalParcel;
import isep.model.Cultivation;
import isep.model.IrrigationPlan;
import isep.model.ParcelIrrigationWrapper;
import isep.model.RegularityAll;
import isep.model.RegularityEven;
import isep.model.RegularityOdd;
import isep.model.store.AgriculturalParcelStore;
import isep.model.store.CultivationStore;
import isep.shared.Constants;
import isep.shared.Hour;
import isep.shared.exceptions.InvalidHourFormatException;

public class IrrigationPlanMock {
  public static IrrigationPlan mockIrrigationPlan() throws InvalidHourFormatException {
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

    List<ParcelIrrigationWrapper> parcels = new ArrayList<>();
    parcels.add(new ParcelIrrigationWrapper(p1, 15, new RegularityAll()));
    parcels.add(new ParcelIrrigationWrapper(p2, 20, new RegularityOdd()));
    parcels.add(new ParcelIrrigationWrapper(p3, 12, new RegularityEven()));

    // today's date
    Calendar startDate = Calendar.getInstance();
    // startDate.set(2022, 11, 20);

    return new IrrigationPlan(hours, parcels, startDate,
        Constants.DEFAULT_IRRIGATION_PLAN_DURATION);
  }

  public static List<String> mockIrrigationPlanFile1() {
    List<String> data = new ArrayList<>();
    data.add("8h30,15h00");
    data.add("Parcela1,15,t");
    data.add("Parcela2,20,i");
    data.add("Parcela3,12,p");
    return data;
  }
}
