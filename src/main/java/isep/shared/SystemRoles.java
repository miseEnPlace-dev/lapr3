package isep.shared;

public enum SystemRoles {
  GESTOR_AGRICOLA("Gestor Agricola"), GESTOR_DISTRIBUICAO("Gestor Distribuicao"), CONDUTOR(
      "Condutor"), CLIENTE("Cliente");

  private String description = "";

  private SystemRoles(String description) {
    this.description = description;
  }

  public String toString() {
    return this.description;
  }
}
