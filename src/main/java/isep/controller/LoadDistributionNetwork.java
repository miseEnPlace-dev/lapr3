package isep.controller;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import isep.model.Company;
import isep.model.DistributionNetwork;
import isep.model.Role;
import isep.utils.CSVReader;

public class LoadDistributionNetwork {
  private Company company;
  private CSVReader distanceReader;
  private CSVReader localizationsReader;

  public LoadDistributionNetwork(Company company, String distancesFileName, String localizationsFileName)
      throws FileNotFoundException {
    this.company = company;
    this.distanceReader = new CSVReader(distancesFileName);
    this.localizationsReader = new CSVReader(localizationsFileName);
  }

  private Role getRole(String id) {
    String letter = id.substring(0, 1).toLowerCase();

    if (letter.equals('c'))
      return Role.CLIENT;
    if (letter.equals('p'))
      return Role.PRODUCER;
    if (letter.equals('e'))
      return Role.ENTERPRISE;

    return null;
  }

  public DistributionNetwork loadDistributionNetwork() {
    // TODO
    System.out.println("Reading localizations...");
    List<Map<String, String>> localizations = localizationsReader.read();

    for (Map<String, String> localization : localizations) {
      try {
        String id = localization.get("Clientes-Produtores");
        double latitude = Double.parseDouble(localization.get("lat"));
        double longitude = Double.parseDouble(localization.get("lng"));
        String localizationId = localization.get("Loc id");
        Role role = getRole(id);

        this.company.getEntityStore().addEntity(id, latitude, longitude, localizationId, role);
      } catch (NumberFormatException ex) {
        System.out.println("Error parsing localization: " + localization);
      }
    }

    System.out.println("Reading distances...");
    List<Map<String, String>> distances = distanceReader.read();
    return null;
  }
}
