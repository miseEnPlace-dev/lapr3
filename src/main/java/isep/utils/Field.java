package isep.utils;

public enum Field {
  CLIENTPROD("Clientes-Produtores"), DAY("Dia"), PRODUCT("Prod");

  public final String name;

  private Field(String name) {
    this.name = name;
  }
}
