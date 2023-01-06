package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {
  @Test
  public void testCreateProduct() {
    Product product = new Product("Test");
    assertEquals("Test", product.getName());
  }

  @Test
  public void testCreateProductWithNullName() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Product(null);
    });
  }

  @Test
  public void testCreateProductWithInvalidName() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Product("");
    });
  }

  @Test
  public void testEqualsWithNull() {
    Product product = new Product("Test");
    assertEquals(false, product.equals(null));
  }

  @Test
  public void testEqualsWithSameObject() {
    Product product = new Product("Test");
    assertEquals(true, product.equals(product));
  }

  @Test
  public void testEqualsWithDifferentClass() {
    Product product = new Product("Test");
    assertEquals(false, product.equals(new Object()));
  }

  @Test
  public void testEqualsWithDifferentName() {
    Product product = new Product("Test");
    Product product2 = new Product("Test2");
    assertEquals(false, product.equals(product2));
  }

  @Test
  public void testEqualsWorks() {
    Product product = new Product("Test");
    Product product2 = new Product("Test");
    assertEquals(true, product.equals(product2));
  }
}
