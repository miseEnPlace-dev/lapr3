package isep.controller;

import java.util.List;
import java.util.Map;

import isep.model.DistributionNetwork;
import isep.model.Entity;
import isep.model.store.EntityStore;
import isep.shared.Constants;

/*
 * US 301 - Controller that loads a distribution network
 *
 * @author Tomás Lopes <1211289@isep.ipp.pt>
 *
 */
public class LoadDistributionNetworkController {
  private EntityStore entityStore;
  private List<Map<String, String>> distancesData;

  /*
   * Constructor
   */
  public LoadDistributionNetworkController(EntityStore entityStore, List<Map<String, String>> distancesData) {
    this.entityStore = entityStore;
    this.distancesData = distancesData;
  }

  /*
   * Loads a distribution network
   */
  public DistributionNetwork loadDistributionNetwork() {
    DistributionNetwork distributionNetwork = new DistributionNetwork();

    System.out.println("Loading distribution network...");
    for (Map<String, String> distance : distancesData) {
      try {
        String id1 = distance.get(Constants.DISTANCES_LOC1_ID_FIELD_NAME);
        String id2 = distance.get(Constants.DISTANCES_LOC2_ID_FIELD_NAME);
        int distanceValue = Integer.parseInt(distance.get(Constants.DISTANCES_DISTANCE_FIELD_NAME));

        Entity entity1 = this.entityStore.getEntityByLocalizationId(id1);
        Entity entity2 = this.entityStore.getEntityByLocalizationId(id2);

        if (entity1 != null && entity2 != null)
          distributionNetwork.addRelation(entity1, entity2, distanceValue);
      } catch (NumberFormatException ex) {
        System.out.println("Error parsing distance: " + distance);
      }
    }

    System.out.println("Distribution network loaded with success!");
    return distributionNetwork;
  }
}
