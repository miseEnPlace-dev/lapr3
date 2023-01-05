package isep.ui;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import isep.controller.LoadBasketsController;
import isep.ui.utils.Utils;

public class ImportBasketListUI implements Runnable {
  private LoadBasketsController ctrler;

  public ImportBasketListUI() {
    this.ctrler = new LoadBasketsController();
  }

  @Override
  public void run() {
    if (ctrler.isNetworkEmpty()) {
      System.out.println("\nYou must load a distribution network first");
      return;
    }
    String filePath = Utils.readLineFromConsole("\nInsert the file path: ");
    ctrler.setFilename(filePath);

    try {
      List<Map<String, String>> data = ctrler.readData();
      int count = ctrler.mapBaskets(data);
      System.out.println("\nSuccessfully imported " + count + " baskets!");
    } catch (FileNotFoundException e) {
      System.out.println("\nFile does not exist.");
    }

  }
}
