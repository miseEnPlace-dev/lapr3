package isep.model;

public enum ERegularity {
  TODOS("RegularityAll"), PARES("RegularityEven"), IMPARES("RegularityOdd");

  public final String name;
  public static final String prefix = "isep.model.";

  private ERegularity(String name) {
    this.name = name;
  }

  public static String getClassName(String reg) {
    for (ERegularity regularity : ERegularity.values()) {
      if (regularity.name().equals(reg.toUpperCase()))
        return prefix + regularity.name;
    }
    return null;
  }
}
