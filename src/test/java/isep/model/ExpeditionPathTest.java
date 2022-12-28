package isep.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import isep.controller.DefineHubsController;
import isep.controller.ExpeditionPathController;
import isep.shared.exceptions.InvalidHubException;
import isep.shared.exceptions.InvalidNumberOfHubsException;
import isep.shared.exceptions.InvalidOrderException;
import isep.shared.exceptions.InvalidProductNameException;

/**
 * Tests for ExpeditionPath class.
 *
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExpeditionPathTest {
  private DistributionNetwork distributionNetwork;
  private ExpeditionList expeditionList;

  private Basket dAliceBasket;
  private Basket dManuelaBasket;
  private Basket dLuisaBasket;
  private Producer srManuelPorto;
  private Producer srFernandoLisboa;
  private Producer srJoseFaro;
  private Producer srAntonioGuarda;
  private Enterprise hubAveiro;
  private Enterprise hubCoimbra;
  private Enterprise hubLeiria;
  private Enterprise hubViseu;

  @BeforeAll
  public void setUp() throws FileNotFoundException, InvalidNumberOfHubsException, InvalidProductNameException,
      InvalidOrderException, InvalidHubException {
    this.distributionNetwork = new DistributionNetwork();

    Product macaDeAlcobaca = new Product("Maca de Alcobaca");
    Product bananaDaMadeira = new Product("Banana da Madeira");
    Product batataVermelha = new Product("Batata Vermelha");

    // Porto coordinates
    srManuelPorto = new Producer("SR_MANUEL_PORTO", 41.14961, -8.61099, "OPO");
    // Lisbon coordinates
    srFernandoLisboa = new Producer("SR_FERNANDO_LISBOA", 38.72225, -9.13934, "LIS");
    // Faro coordinates
    srJoseFaro = new Producer("SR_JOSE_FARO", 37.01987, -7.93206, "FAO");
    // Guarda coordinates
    srAntonioGuarda = new Producer("SR_ANTONIO_GUARDA", 40.53726, -7.26337, "GUA");

    // Braga coordinates
    Client dAliceBraga = new Client("D_ALICE_BRAGA", 41.54545, -8.42653, "BGZ");
    // Castelo Branco coordinates
    Client dManuelaCBranco = new Client("D_MANUELA_CBRANCO", 39.81909, -7.43889, "CBR");
    // Vila real coordinates
    Client dLuisaVReal = new Client("D_LUISA_VREAL", 41.30045, -7.74482, "VRL");

    // Aveiro coordinates
    hubAveiro = new Enterprise("HUB_AVEIRO", 40.64427, -8.64554, "AVE");
    // Coimbra coordinates
    hubCoimbra = new Enterprise("HUB_COIMBRA", 40.20331, -8.41025, "COI");
    // Leiria coordinates
    hubLeiria = new Enterprise("HUB_LEIRIA", 39.74383, -8.80777, "LEI");
    // Viseu coordinates
    hubViseu = new Enterprise("HUB_VISEU", 40.65716, -7.90907, "VIS");

    this.distributionNetwork.addRelation(srManuelPorto, hubAveiro, 75);
    this.distributionNetwork.addRelation(srManuelPorto, dAliceBraga, 60);
    this.distributionNetwork.addRelation(srManuelPorto, dLuisaVReal, 100);
    this.distributionNetwork.addRelation(hubViseu, srAntonioGuarda, 75);
    this.distributionNetwork.addRelation(srAntonioGuarda, dManuelaCBranco, 100);
    this.distributionNetwork.addRelation(hubAveiro, hubCoimbra, 60);
    this.distributionNetwork.addRelation(hubCoimbra, srFernandoLisboa, 200);
    this.distributionNetwork.addRelation(hubCoimbra, hubLeiria, 80);
    this.distributionNetwork.addRelation(hubAveiro, hubLeiria, 120);
    this.distributionNetwork.addRelation(hubLeiria, srFernandoLisboa, 150);
    this.distributionNetwork.addRelation(hubAveiro, hubViseu, 85);
    this.distributionNetwork.addRelation(hubLeiria, dManuelaCBranco, 170);
    this.distributionNetwork.addRelation(srFernandoLisboa, srJoseFaro, 280);

    DefineHubsController defineHubsController = new DefineHubsController(this.distributionNetwork);
    defineHubsController.defineHubs(4);

    Map<Product, Integer> srManuelOrderedProducts = new HashMap<Product, Integer>();
    srManuelOrderedProducts.put(macaDeAlcobaca, 10);
    Map<Product, Integer> srFernandoOrderedProducts = new HashMap<Product, Integer>();
    srFernandoOrderedProducts.put(bananaDaMadeira, 10);
    Map<Product, Integer> srManuelAndFernandoOrderedProducts = new HashMap<Product, Integer>();
    srManuelAndFernandoOrderedProducts.put(macaDeAlcobaca, 10);
    srManuelAndFernandoOrderedProducts.put(bananaDaMadeira, 10);

    Map<Producer, Map<Product, Integer>> srManuelReceivedProducts = new HashMap<Producer, Map<Product, Integer>>();
    srManuelReceivedProducts.put(srManuelPorto, srManuelOrderedProducts);
    Map<Producer, Map<Product, Integer>> srFernandoReceivedProducts = new HashMap<Producer, Map<Product, Integer>>();
    srFernandoReceivedProducts.put(srFernandoLisboa, srFernandoOrderedProducts);
    Map<Producer, Map<Product, Integer>> srManuelAndFernandoReceivedProducts = new HashMap<Producer, Map<Product, Integer>>();
    srManuelAndFernandoReceivedProducts.put(srManuelPorto, srManuelOrderedProducts);
    srManuelAndFernandoReceivedProducts.put(srFernandoLisboa, srFernandoOrderedProducts);

    dAliceBasket = new Basket(srManuelOrderedProducts, srManuelReceivedProducts, hubAveiro, dAliceBraga);
    dManuelaBasket = new Basket(srFernandoOrderedProducts, srFernandoReceivedProducts, hubViseu, dManuelaCBranco);
    dLuisaBasket = new Basket(srManuelAndFernandoOrderedProducts, srManuelAndFernandoReceivedProducts, hubAveiro,
        dLuisaVReal);
  }

  @Test
  public void testWithZeroBaskets() {
    this.expeditionList = new ExpeditionList(0);

    ExpeditionPathController expeditionPathController = new ExpeditionPathController(this.distributionNetwork,
        this.expeditionList);
    ExpeditionPath path = expeditionPathController.findExpeditionPath();

    List<Entity> expected = new ArrayList<>();

    path.printPath();
    assertEquals(expected, path.getPathList());
    assertEquals(0, path.getTotalDistance());
  }

  @Test
  public void testWithDAliceBasket() {
    this.expeditionList = new ExpeditionList(1);
    this.expeditionList.addBasket(dAliceBasket);

    ExpeditionPathController expeditionPathController = new ExpeditionPathController(this.distributionNetwork,
        this.expeditionList);
    ExpeditionPath path = expeditionPathController.findExpeditionPath();

    List<Entity> expected = new ArrayList<>();
    expected.add(srManuelPorto);
    expected.add(hubAveiro);

    path.printPath();
    assertEquals(expected, path.getPathList());
    assertEquals(75, path.getTotalDistance());
  }

  @Test
  public void testWithDManuelaBasket() {
    this.expeditionList = new ExpeditionList(2);
    this.expeditionList.addBasket(dManuelaBasket);

    ExpeditionPathController expeditionPathController = new ExpeditionPathController(this.distributionNetwork,
        this.expeditionList);
    ExpeditionPath path = expeditionPathController.findExpeditionPath();

    List<Entity> expected = new ArrayList<>();
    expected.add(srFernandoLisboa);
    expected.add(hubCoimbra);
    expected.add(hubAveiro);
    expected.add(hubViseu);

    path.printPath();
    assertEquals(expected, path.getPathList());
    assertEquals(345, path.getTotalDistance());
  }

  @Test
  public void testWithDLuisaBasket() {
    this.expeditionList = new ExpeditionList(3);
    this.expeditionList.addBasket(dLuisaBasket);

    ExpeditionPathController expeditionPathController = new ExpeditionPathController(this.distributionNetwork,
        this.expeditionList);
    ExpeditionPath path = expeditionPathController.findExpeditionPath();

    List<Entity> expected = new ArrayList<>();
    expected.add(srManuelPorto);
    expected.add(hubAveiro);
    expected.add(hubLeiria);
    expected.add(srFernandoLisboa);
    expected.add(hubCoimbra);
    expected.add(hubAveiro);

    path.printPath();
    assertEquals(expected, path.getPathList());
    assertEquals(605, path.getTotalDistance());
  }
}
