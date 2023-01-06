package isep.model;

public class Product {
  private String name;

  public Product(String name) {
    setName(name);
  }

  public String getName() {
    return this.name;
  }

  private void setName(String name) {
    if (name == "" || name == null)
      throw new IllegalArgumentException("Product name cannot be empty or null");

    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!(obj instanceof Product))
      return false;

    Product other = (Product) obj;
    return this.name.equals(other.name);
  }
}
