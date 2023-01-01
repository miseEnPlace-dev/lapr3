package isep.shared;

public enum SystemRole {
  GESTOR_AGRICOLA("Gestor Agricola"), GESTOR_DISTRIBUICAO("Gestor Distribuicao"), CONDUTOR(
      "Condutor"), CLIENTE("Cliente");

  private String description = "";

  private SystemRole(String description) {
    this.description = description;
  }

  public String toString() {
    return this.description;
  }
}
