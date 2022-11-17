package isep.model.store;

import java.util.ArrayList;
import java.util.List;

import isep.model.Entity;
import isep.model.Role;

public class EntityStore {
  private List<Entity> entities;

  public EntityStore() {
    this.entities = new ArrayList<Entity>();
  }

  public boolean addEntity(String id, double latitude, double longitude, String localizationId, Role role) {
    Entity entity = new Entity(id, latitude, longitude, localizationId, role);
    return entities.add(entity);
  }
}
