package isep.model.mapper;

import isep.model.Client;
import isep.model.Enterprise;
import isep.model.Entity;
import isep.model.Producer;
import isep.model.Role;

public class RoleMapper {
  public Class<? extends Entity> toClass(Role role) {
    switch (role) {
      case CLIENT:
        return (Class<Client>) Client.class;
      case PRODUCER:
        return (Class<Producer>) Producer.class;
      case ENTERPRISE:
        return (Class<Enterprise>) Enterprise.class;
    }
    return null;
  }
}
