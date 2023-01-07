package isep.model;

import isep.model.store.AgriculturalParcelStore;
import isep.model.store.CultivationStore;
import isep.model.store.EntityStore;

/**
 * @author André Barros <1211299@isep.ipp.pt>
 * @author Carlos Lopes <1211277@isep.ipp.pt>
 * @author Ricardo Moreira <1211285@isep.ipp.pt>
 * @author Tomás Lopes <1211289@isep.ipp.pt>
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class Company {
  private final EntityStore entityStore;
  private final AgriculturalParcelStore agriculturalParcelStore;
  private final CultivationStore cultivationStore;

  private DistributionNetwork distributionNetwork;
  private ExpeditionList currentExpeditionList;
  private String currentEntitiesFilePath = null;
  private String currentDistancesFilePath = null;
  private String currentBasketsFilePath = null;

  public Company() {
    this.distributionNetwork = new DistributionNetwork();
    this.entityStore = new EntityStore();
    this.agriculturalParcelStore = new AgriculturalParcelStore();
    this.cultivationStore = new CultivationStore();
  }

  public DistributionNetwork getDistributionNetwork() {
    return this.distributionNetwork;
  }

  public ExpeditionList getCurrentExpeditionList() {
    return this.currentExpeditionList;
  }

  public String getCurrentEntitiesFilePath() {
    return currentEntitiesFilePath;
  }

  public void setCurrentEntitiesFilePath(String currentEntitiesFilePath) {
    this.currentEntitiesFilePath = currentEntitiesFilePath;
  }

  public String getCurrentDistancesFilePath() {
    return currentDistancesFilePath;
  }

  public void setCurrentDistancesFilePath(String currentDistancesFilePath) {
    this.currentDistancesFilePath = currentDistancesFilePath;
  }

  public String getCurrentBasketsFilePath() {
    return currentBasketsFilePath;
  }

  public void setCurrentBasketsFilePath(String currentBasketsFilePath) {
    this.currentBasketsFilePath = currentBasketsFilePath;
  }

  public void setDistributionNetwork(DistributionNetwork distributionNetwork) {
    this.distributionNetwork = distributionNetwork;
  }

  public void setCurrentExpeditionList(ExpeditionList currentExpeditionList) {
    this.currentExpeditionList = currentExpeditionList;
  }

  public EntityStore getEntityStore() {
    return entityStore;
  }

  public AgriculturalParcelStore getAgriculturalParcelStore() {
    return agriculturalParcelStore;
  }

  public CultivationStore getCultivationStore() {
    return cultivationStore;
  }
}
