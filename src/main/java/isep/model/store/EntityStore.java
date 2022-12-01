package isep.model.store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import isep.model.Client;
import isep.model.Enterprise;
import isep.model.Entity;
import isep.model.Producer;
import isep.model.Role;
import isep.shared.Constants;

public class EntityStore {
  private List<Entity> entities;

  public EntityStore() {
    this.entities = new ArrayList<Entity>();
  }

  public boolean addEntity(String id, double latitude, double longitude, String localizationId, Role role) {
    Entity entity = null;
    switch (role) {
      case PRODUCER:
        entity = new Producer(id, latitude, longitude, localizationId);
        break;
      case CLIENT:
        entity = new Client(id, latitude, longitude, localizationId);
        break;
      case ENTERPRISE:
        entity = new Enterprise(id, latitude, longitude, localizationId);
        break;
    }
    return entities.add(entity);
  }

  public Entity getEntityByLocalizationId(String localizationId) {
    for (Entity entity : entities)
      if (entity.getLocalizationId().equals(localizationId))
        return entity;

    return null;
  }

  private Role mapIdToRole(String id) {
    String letter = id.substring(0, 1).toLowerCase();

    if (letter.equals("c"))
      return Role.CLIENT;
    if (letter.equals("p"))
      return Role.PRODUCER;
    if (letter.equals("e"))
      return Role.ENTERPRISE;
    return null;
  }

  public List<Entity> getEntities() {
    return entities;
  }

  public void addEntitiesFromList(List<Map<String, String>> localizations) {
    for (Map<String, String> localization : localizations) {
      try {
        String id = localization.get(Constants.LOCALIZATION_ID_FIELD_NAME);
        double latitude = Double.parseDouble(localization.get(Constants.LOCALIZATION_LATITUDE_FIELD_NAME));
        double longitude = Double.parseDouble(localization.get(Constants.LOCALIZATION_LONGITUDE_FIELD_NAME));
        String localizationId = localization.get(Constants.LOCALIZATION_LOCALIZATION_ID_FIELD_NAME);
        Role role = mapIdToRole(id);

        this.addEntity(id, latitude, longitude, localizationId, role);
      } catch (NumberFormatException ex) {
        System.out.println("Error parsing localization: " + localization);
      }
    }
  }
}
