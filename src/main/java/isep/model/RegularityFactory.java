package isep.model;

import isep.shared.exceptions.InvalidFileFormatException;

public class RegularityFactory {
  private RegularityFactory() {}

  public static Regularity getRegularity(String name) throws InvalidFileFormatException {
    String className = ERegularity.getClassName(name);

    try {
      Class<?> regularityClass = Class.forName(className);
      Regularity regularity = (Regularity) regularityClass.getDeclaredConstructor().newInstance();
      return regularity;
    } catch (ClassNotFoundException e) {
      throw new InvalidFileFormatException(String.format("Invalid regularity %s", name));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
