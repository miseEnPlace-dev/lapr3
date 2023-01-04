package isep.shared;

public enum SystemRole {
  AGRICULTURAL_MANAGER("Agricola"), DISTRIBUTION_MANAGER("Distribuicao"), DRIVER("Condutor"),
  CLIENT("Cliente");

  private String description = "";

  private SystemRole(String description) {
    this.description = description;
  }

  public String toString() {
    return this.description;
  }
}
